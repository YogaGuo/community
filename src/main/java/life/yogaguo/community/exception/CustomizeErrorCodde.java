package life.yogaguo.community.exception;

/**
 *
 */
public enum CustomizeErrorCodde implements ICustomizeErrorCode {
    /**
     *
     */
    QUESTION_NOT_FOUND(2001,"尴尬..问题不在了，换个试试?"),
    TARGET_PARAM_NOT_FOUND(2002,"未选择任何问题或评论进行回复"),
    NO_LOGIN(2003,"未登陆"),
    SYSTEM_ERROR(2004,"技能冷却中"),
    TYPE_PARAM_ERROR(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在"),
    COMMENT_IS_EMPTY(2007,"回复内容不能为空");
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private String message;
    private Integer code;
    CustomizeErrorCodde(Integer code,String message) {
        this.message = message;
        this.code = code;
    }
}
