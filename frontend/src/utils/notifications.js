import { toast } from 'react-toastify'

export const MessageType = {
  ERROR: 1,
  SUCCESS: 2,
  WARNING: 3,
  INFO: 4
}


export function createNotification (type, message, millis, callback) {
  switch (type) {
    case MessageType.INFO: {
      toast.info(message, {
        autoClose: millis,
        position: toast.POSITION.TOP_RIGHT,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        onClose: callback
      })
      break
    }
    case MessageType.SUCCESS: {
      toast.success(message, {
        autoClose: millis,
        position: toast.POSITION.TOP_RIGHT,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        onClose: callback
      })
      break
    }
    case MessageType.WARNING: {
      toast.warn(message, {
        autoClose: millis,
        position: toast.POSITION.TOP_RIGHT,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        onClose: callback
      })
      break
    }
    case MessageType.ERROR: {
      toast.error(message, {
        autoClose: millis,
        position: toast.POSITION.TOP_RIGHT,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        onClose: callback
      })
      break
    }
    default: {
      throw new Error('Type should be a MessageType object')
    }
  }
}
