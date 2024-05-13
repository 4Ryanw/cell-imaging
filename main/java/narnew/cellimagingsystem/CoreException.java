package narnew.cellimagingsystem;

import narnew.cellimagingsystem.enums.ErrorCodeEnum;

/**
 * TODO
 *
 * @author narnew
 * @since 2024年05月12日
 */

public class CoreException extends RuntimeException {
    private ErrorCodeEnum codeEnum;

    public CoreException(ErrorCodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.codeEnum = codeEnum;
    }

    public CoreException(ErrorCodeEnum codeEnum, Throwable cause) {
        super(codeEnum.getMessage(), cause);
        this.codeEnum = codeEnum;
    }

    public CoreException(ErrorCodeEnum codeEnum, String msg) {
        super(msg);
        this.codeEnum = codeEnum;
    }

    public ErrorCodeEnum getCodeEnum() {
        return this.codeEnum;
    }
}

