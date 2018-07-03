package top.gotoeasy.framework.core.btf

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification
import top.gotoeasy.framework.core.btf.impl.DefaultBlockTextFile
import top.gotoeasy.framework.core.util.CmnResource


class BlockTextFileTest extends Specification {

    @Test
    def void "按文件内容解析"() {
        expect:

        when:
        def btf = new DefaultBlockTextFile();
        List<Map<String, String>> list = btf.parse(CmnResource.getContext("BlockTextFileSample.btf"))
        System.err.println(list)

        then:
        list.size() == 2
        btf.getBlockText("HTML") != null
        btf.getBlockText("js") != null
        btf.getBlockText("test") == "haha"

        list.get(1).get("HTML") !=  btf.getBlockText("HTML")
        list.get(1).get("js") ==  btf.getBlockText("js")
    }
}
