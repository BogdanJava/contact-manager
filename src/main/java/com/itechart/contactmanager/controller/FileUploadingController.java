package com.itechart.contactmanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;

@RestController
@RequestMapping("/api/files")
public class FileUploadingController {

    private static final String UPLOAD_DIR = "uploads";

    @GetMapping
    public ResponseEntity upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int resumableChunkNumber = getResumableChunkNumber(request);

        ResumableInfo info = getResumableInfo(request);

        if (info.uploadedChunks.contains(new ResumableInfo.ResumableChunkNumber(resumableChunkNumber))) {
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded"); //This Chunk has been Uploaded.
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("KEK");
        }
    }

    @PostMapping
    public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int resumableChunkNumber = getResumableChunkNumber(request);

        ResumableInfo info = getResumableInfo(request);

        RandomAccessFile raf = new RandomAccessFile(info.resumableFilePath, "rw");

        //Seek to position
        raf.seek((resumableChunkNumber - 1) * (long) info.resumableChunkSize);

        //Save to file
        InputStream is = request.getInputStream();
        long readed = 0;
        long content_length = request.getContentLength();
        byte[] bytes = new byte[1024 * 100];
        while (readed < content_length) {
            int r = is.read(bytes);
            if (r < 0) {
                break;
            }
            raf.write(bytes, 0, r);
            readed += r;
        }
        raf.close();

        //Mark as uploaded.
        info.uploadedChunks.add(new ResumableInfo.ResumableChunkNumber(resumableChunkNumber));
        if (info.checkIfUploadFinished()) { //Check if all chunks uploaded, and change filename
            ResumableInfoStorage.getInstance().remove(info);
            response.getWriter().print("All finished.");
        } else {
            response.getWriter().print("Upload");
        }
    }

    private int getResumableChunkNumber(HttpServletRequest request) {
        return HttpUtils.toInt(request.getParameter("resumableChunkNumber"), -1);
    }

    private ResumableInfo getResumableInfo(HttpServletRequest request) throws ServletException {
        String base_dir = UPLOAD_DIR;

        int resumableChunkSize = HttpUtils.toInt(request.getParameter("resumableChunkSize"), -1);
        long resumableTotalSize = HttpUtils.toLong(request.getParameter("resumableTotalSize"), -1);
        String resumableIdentifier = request.getParameter("resumableIdentifier");
        String resumableFilename = request.getParameter("resumableFilename");
        String resumableRelativePath = request.getParameter("resumableRelativePath");
        //Here we add a ".temp" to every upload file to indicate NON-FINISHED
        new File(base_dir).mkdir();
        String resumableFilePath = new File(base_dir, resumableFilename).getAbsolutePath() + ".temp";

        ResumableInfoStorage storage = ResumableInfoStorage.getInstance();

        ResumableInfo info = storage.get(resumableChunkSize, resumableTotalSize,
                resumableIdentifier, resumableFilename, resumableRelativePath, resumableFilePath);
        if (!info.vaild()) {
            storage.remove(info);
            throw new ServletException("Invalid request params.");
        }
        return info;
    }

    static class ResumableInfoStorage {

        private static ResumableInfoStorage sInstance;
        //resumableIdentifier --  ResumableInfo
        private HashMap<String, ResumableInfo> mMap = new HashMap<String, ResumableInfo>();

        //Single instance
        private ResumableInfoStorage() {
        }

        public static synchronized ResumableInfoStorage getInstance() {
            if (sInstance == null) {
                sInstance = new ResumableInfoStorage();
            }
            return sInstance;
        }

        /**
         * Get ResumableInfo from mMap or Create a new one.
         *
         * @param resumableChunkSize
         * @param resumableTotalSize
         * @param resumableIdentifier
         * @param resumableFilename
         * @param resumableRelativePath
         * @param resumableFilePath
         * @return
         */
        public synchronized ResumableInfo get(int resumableChunkSize, long resumableTotalSize,
                                              String resumableIdentifier, String resumableFilename,
                                              String resumableRelativePath, String resumableFilePath) {

            ResumableInfo info = mMap.get(resumableIdentifier);

            if (info == null) {
                info = new ResumableInfo();

                info.resumableChunkSize = resumableChunkSize;
                info.resumableTotalSize = resumableTotalSize;
                info.resumableIdentifier = resumableIdentifier;
                info.resumableFilename = resumableFilename;
                info.resumableRelativePath = resumableRelativePath;
                info.resumableFilePath = resumableFilePath;

                mMap.put(resumableIdentifier, info);
            }
            return info;
        }

        /**
         * É¾³ýResumableInfo
         *
         * @param info
         */
        public void remove(ResumableInfo info) {
            mMap.remove(info.resumableIdentifier);
        }
    }

    static class HttpUtils {

        public static boolean isEmpty(String value) {
            return value == null || "".equals(value);
        }

        /**
         * Convert String to long
         *
         * @param value
         * @param def   default value
         * @return
         */
        public static long toLong(String value, long def) {
            if (isEmpty(value)) {
                return def;
            }

            try {
                return Long.valueOf(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return def;
            }
        }

        /**
         * Convert String to int
         *
         * @param value
         * @param def   default value
         * @return
         */
        public static int toInt(String value, int def) {
            if (isEmpty(value)) {
                return def;
            }
            try {
                return Integer.valueOf(value);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return def;
            }
        }
    }

    static class ResumableInfo {

        public int resumableChunkSize;
        public long resumableTotalSize;
        public String resumableIdentifier;
        public String resumableFilename;
        public String resumableRelativePath;
        //Chunks uploaded
        public HashSet<ResumableChunkNumber> uploadedChunks = new HashSet<ResumableChunkNumber>();
        public String resumableFilePath;

        public boolean vaild() {
            if (resumableChunkSize < 0 || resumableTotalSize < 0
                    || HttpUtils.isEmpty(resumableIdentifier)
                    || HttpUtils.isEmpty(resumableFilename)
                    || HttpUtils.isEmpty(resumableRelativePath)) {
                return false;
            } else {
                return true;
            }
        }

        public boolean checkIfUploadFinished() {
            //check if upload finished
            int count = (int) Math.ceil(((double) resumableTotalSize) / ((double) resumableChunkSize));
            for (int i = 1; i < count; i++) {
                if (!uploadedChunks.contains(new ResumableChunkNumber(i))) {
                    return false;
                }
            }

            //Upload finished, change filename.
            File file = new File(resumableFilePath);
            String new_path = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - ".temp".length());
            file.renameTo(new File(new_path));
            return true;
        }

        public static class ResumableChunkNumber {
            public int number;

            public ResumableChunkNumber(int number) {
                this.number = number;
            }

            @Override
            public boolean equals(Object obj) {
                return obj instanceof ResumableChunkNumber
                        ? ((ResumableChunkNumber)obj).number == this.number : false;
            }

            @Override
            public int hashCode() {
                return number;
            }
        }
    }
}
