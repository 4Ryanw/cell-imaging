package narnew.cellimagingsystem.base;

/**
 * TODO
 *
 * @author narnew
 * @since 2024年05月12日
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import narnew.cellimagingsystem.enums.ErrorCodeEnum;
import narnew.cellimagingsystem.enums.MsgTypeEnum;

public class Response<T> {
    private boolean success;
    private Integer statusCode;
    private T data;
    private String message;
    private MsgTypeEnum msgType;

    public static <T> Response<T> with() {
        return with(ErrorCodeEnum.SUCCESS, null);
    }

    public static <T> Response<T> with(T data) {
        return with(ErrorCodeEnum.SUCCESS, data);
    }

    public static Response<String> withMsg(String msg) {
        return with(ErrorCodeEnum.SUCCESS, msg, msg, (MsgTypeEnum)null);
    }

    public static Response<String> withMsg(String msg, MsgTypeEnum msgType) {
        return with(ErrorCodeEnum.SUCCESS, msg, msg, msgType);
    }

    public static <T> Response<T> with(ErrorCodeEnum errorCode, T data) {
        return with(errorCode, data, errorCode.getMessage(), (MsgTypeEnum)null);
    }

    public static <T> Response<T> with(ErrorCodeEnum errorCode, T data, MsgTypeEnum msgType) {
        return with(errorCode, data, errorCode.getMessage(), msgType);
    }

    public static <T> Response<T> with(ErrorCodeEnum errorCode, T data, String msg, MsgTypeEnum msgType) {
        boolean isSuccess = ErrorCodeEnum.SUCCESS.equals(errorCode);
        if (null == msgType) {
            msgType = isSuccess ? MsgTypeEnum.INFO : MsgTypeEnum.ERROR;
        }

        return (Response<T>) builder().data(data).statusCode(errorCode.getErrorCode()).success(isSuccess).msgType(msgType).message(msg).build();
    }

    public static boolean equalErrorCode(Response response, ErrorCodeEnum codeEnum) {
        return null != response && !response.isSuccess() && null != codeEnum ? response.getStatusCode().equals(codeEnum.getErrorCode()) : false;
    }

    public static <T> ResponseBuilder<T> builder() {
        return new ResponseBuilder();
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public T getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public MsgTypeEnum getMsgType() {
        return this.msgType;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMsgType(MsgTypeEnum msgType) {
        this.msgType = msgType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Response)) {
            return false;
        } else {
            Response<?> other = (Response)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isSuccess() != other.isSuccess()) {
                return false;
            } else {
                label61: {
                    Object this$statusCode = this.getStatusCode();
                    Object other$statusCode = other.getStatusCode();
                    if (this$statusCode == null) {
                        if (other$statusCode == null) {
                            break label61;
                        }
                    } else if (this$statusCode.equals(other$statusCode)) {
                        break label61;
                    }

                    return false;
                }

                label54: {
                    Object this$data = this.getData();
                    Object other$data = other.getData();
                    if (this$data == null) {
                        if (other$data == null) {
                            break label54;
                        }
                    } else if (this$data.equals(other$data)) {
                        break label54;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$msgType = this.getMsgType();
                Object other$msgType = other.getMsgType();
                if (this$msgType == null) {
                    if (other$msgType != null) {
                        return false;
                    }
                } else if (!this$msgType.equals(other$msgType)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Response;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + (this.isSuccess() ? 79 : 97);
        Object $statusCode = this.getStatusCode();
        result = result * 59 + ($statusCode == null ? 43 : $statusCode.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $msgType = this.getMsgType();
        result = result * 59 + ($msgType == null ? 43 : $msgType.hashCode());
        return result;
    }

    public Response() {
    }

    public Response(boolean success, Integer statusCode, T data, String message, MsgTypeEnum msgType) {
        this.success = success;
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
        this.msgType = msgType;
    }

    public String toString() {
        return "Response(success=" + this.isSuccess() + ", statusCode=" + this.getStatusCode() + ", data=" + this.getData() + ", message=" + this.getMessage() + ", msgType=" + this.getMsgType() + ")";
    }

    public static class ResponseBuilder<T> {
        private boolean success;
        private Integer statusCode;
        private T data;
        private String message;
        private MsgTypeEnum msgType;

        ResponseBuilder() {
        }

        public ResponseBuilder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public ResponseBuilder<T> statusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ResponseBuilder<T> msgType(MsgTypeEnum msgType) {
            this.msgType = msgType;
            return this;
        }

        public Response<T> build() {
            return new Response(this.success, this.statusCode, this.data, this.message, this.msgType);
        }

        public String toString() {
            return "Response.ResponseBuilder(success=" + this.success + ", statusCode=" + this.statusCode + ", data=" + this.data + ", message=" + this.message + ", msgType=" + this.msgType + ")";
        }
    }
}
