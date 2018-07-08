import React, {Component} from 'react'
import './UploadFiles.css'
import {withRouter} from 'react-router-dom'
import Resumable from 'resumablejs'
import {API_URL, AUTH_TOKEN} from "../../utils/constants";
import {RingLoader} from 'react-spinners';

class UploadFiles extends Component {
    constructor(props) {
        super(props)
        this.state = {
            fileName: 'No file',
            loading: false,
            resumable: null,
            loadedPercents: 0,
        }
        this.upload = this.upload.bind(this)
    }

    upload(event) {
        this.setState({
            loading: true
        })
        this.state.resumable.upload()
    }

    componentDidMount() {
        let r = new Resumable({
            target: `${API_URL}/files`,
            chunkSize: 1024 * 1024,
            simultaneousUploads: 4,
            testChunks: true,
            method: 'octet',
            throttleProgressCallbacks: 1,
            headers: {
                'Authorization': `Bearer ${localStorage.getItem(AUTH_TOKEN)}`
            }
        });

        if (r.support) {
            let button = document.getElementById('selectFileButton')
            console.log(button)
            r.assignBrowse(button)

            r.on('fileAdded', (file, event) => {
                console.log(file)
                console.log('File added')
                this.setState({
                    fileName: file.fileName
                })
            })

            r.on('complete', () => {
                this.setState({
                    loading: false
                })
                console.log('Complete')
            })

            r.on('fileSuccess', (file, message) => {
                console.log('File success')
                console.log(`Message: ${message}`)
            })

            r.on('chunkingProgress', (file, ratio) => {
                console.log('Chunking preparation: ' + ratio)
            })

            r.on('progress', () => {
                this.setState({
                    loadedPercents: parseInt(`${this.state.resumable.progress() * 100}`)
                })
            })

            this.setState({
                resumable: r,
            })
        }

        document.getElementById('uploadButton').onclick = this.upload
    }

    render() {
        return (
            <div>
                <button className='btn btn-outline-primary' id="selectFileButton">
                    Select file
                </button>
                <input style={{marginLeft: '10px'}} disabled='true' value={this.state.fileName}/>
                <button className='btn btn-success' style={{marginTop: "5px"}} id="uploadButton">
                    Upload
                </button>
                <RingLoader
                    color={'#bc6d36'}
                    loading={this.state.loading}
                />
                <div hidden={!this.state.loading}>Loading {this.state.loadedPercents}%</div>
            </div>

        )
    }
}

export default withRouter(UploadFiles)
