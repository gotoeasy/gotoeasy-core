package top.gotoeasy.framework.core.btf.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import top.gotoeasy.framework.core.btf.BlockTextFile;

/**
 * BTF（Block-Text-File）块文本文件接口的默认实现类
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
public class DefaultBlockTextFile implements BlockTextFile {

    private List<Map<String, String>> list = new ArrayList<>();
    private String                    lf   = "\r\n";

    /**
     * 块文本文件解析
     * 
     * @param text 文件内容
     * @return 块文档列表：List＜Map＜块名，块文本＞＞
     */
    @Override
    public List<Map<String, String>> parse(String text) {
        lf = text.indexOf("\r\n") >= 0 ? "\r\n" : "\n";
        return parse(Arrays.asList(text.split("\\r\\n")));
    }

    /**
     * 块文本文件解析
     * 
     * @param lines 文件内容
     * @return 块文档列表：List＜Map＜块名，块文本＞＞
     */
    @Override
    public List<Map<String, String>> parse(List<String> lines) {

        boolean documentStart = false;
        boolean blockStart = false;
        Map<String, String> map = null;
        String name = null;
        StringBuilder buf = null;
        String tmp = null;
        for ( String line : lines ) {

            if ( isBlockStart(line) ) {
                tmp = getBlockName(line);

                if ( !documentStart ) {
                    map = new LinkedHashMap<>();
                }
                if ( blockStart ) {
                    buf.setLength(buf.length() - lf.length());
                    map.put(name, buf.toString());
                }

                name = tmp;
                buf = new StringBuilder();

                documentStart = true;
                blockStart = true;
            } else if ( isBlockEnd(line) ) {
                if ( blockStart ) {
                    buf.setLength(buf.length() - lf.length());
                    map.put(name, buf.toString());
                }

                name = null;
                buf = null;

                blockStart = false;
            } else if ( isDocumentEnd(line) ) {
                if ( blockStart ) {
                    buf.setLength(buf.length() - lf.length());
                    map.put(name, buf.toString());
                    list.add(map);
                }

                name = null;
                buf = null;
                map = null;

                documentStart = false;
                blockStart = false;
            } else {
                if ( blockStart ) {
                    buf.append(line).append(lf);
                } else {
                    // ignore line
                }
            }

        }

        if ( buf != null ) {
            buf.setLength(buf.length() - lf.length());
            map.put(name, buf.toString());
            list.add(map);
        }

        return list;
    }

    /**
     * 取得首个块文档
     * 
     * @return 块文档：Map＜块名，块文本＞
     */
    @Override
    public Map<String, String> getBlockTextMap() {
        if ( list.isEmpty() ) {
            return new HashMap<>();
        }

        return list.get(0);
    }

    /**
     * 从首个块文档中取得指定块的文本
     * 
     * @param name 块名
     * @return 块文本
     */
    @Override
    public String getBlockText(String name) {
        return getBlockTextMap().get(name.toLowerCase());
    }

    private boolean isBlockStart(String line) {
        return line.startsWith("[") && line.indexOf(']') >= 1;
    }

    private boolean isBlockEnd(String line) {
        return line.startsWith("-----");
    }

    private boolean isDocumentEnd(String line) {
        return line.startsWith("=====");
    }

    private String getBlockName(String line) {
        return line.substring(1, line.indexOf(']')).toLowerCase();
    }

}
