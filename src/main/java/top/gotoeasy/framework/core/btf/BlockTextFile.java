package top.gotoeasy.framework.core.btf;

import java.util.List;
import java.util.Map;

/**
 * BTF（Block-Text-File）块文本文件接口
 * <p>
 * 【文件格式定义】
 * 1）如同INI文件一样分块，固定UTF-8编码，块由‘[’开始‘]’结束，比如[HTML]（结束符后同一行上的字符将忽略<br>
 * 2）块名[name]不区分大小写，主要目的为方便人识别而不是方便机器识别<br>
 * 3）希望明确当前块结束时，单独起一行，用5个半角减号‘-----’开始，标识当前块结束（之后块未开始的行将忽略，可作注释用途）<br>
 * 4）希望明确当前文档块结束时，单独起一行，用5个半角等号‘=====’开始，标识当前文档块结束（概念上如同1个BTF文件包含多个同类INI文件）<br>
 * 5）块文本的最后一个换行符（或回车换行符）将被忽略<br>
 * </p>
 * 
 * @since 2018/07
 * @author 青松
 */
public interface BlockTextFile {

    /**
     * 块文本文件解析
     * 
     * @param text 文件内容
     * @return 块文档列表：List＜Map＜块名，块文本＞＞
     */
    public List<Map<String, String>> parse(String text);

    /**
     * 块文本文件解析
     * 
     * @param lines 文件内容
     * @return 块文档列表：List＜Map＜块名，块文本＞＞
     */
    public List<Map<String, String>> parse(List<String> lines);

    /**
     * 取得首个块文档
     * 
     * @return 块文档：Map＜块名，块文本＞
     */
    public Map<String, String> getBlockTextMap();

    /**
     * 从首个块文档中取得指定块的文本
     * 
     * @return 块文本
     */
    public String getBlockText(String name);

}
