package cn.maycope.util;

/**
 * @Author Maycope
 * @Date 2020/8/21
 * @Version 1.0
 */
public enum ResultCode {
    SUCCESS(2000,"成功"),
    FAIL(5001,"失败"),
    RESULE_DATA_NONE(5002, "数据未找到"),
    ID_IS_ERROR(5003,"id有误"),
    ARTICLE_IS_ERROR(5004,"文章不存在"),
    NAME_OR_TITLED_D(5005,"文章名称或内容不存在"),
    ARTICLE_IS_TRUE(5006,"文章已经存在，请勿重复提交"),
    INSERT_SUCCESS(2001,"文章插入成功"),
    UPDATE_SUCCESS(2002,"文章修改成功");
    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }
    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }


}
