package life.yogaguo.community.ennums;

/**
 *
 */
public enum CommentTypeEnum {
    /**
     * 区分是问题的评论
     */
    QUESTION(1),
    /**
     * 区分是评论的评论
     */
    COMMENT(2);
    /**
     * 评论的类型   QUESTION(1) Or  COMMENT(2);
     */
    private Integer type;

    public static boolean isExist(Integer parentType) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if(value.getType().equals(parentType)){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
