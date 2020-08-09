import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EasyExcelDemo {

    @Test
    public void easyExcelWrite() {
        String filename = "D:\\demo.xlsx";
        EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(getData());
    }

    @Test
    public void easyExcelRead() {
        String filename = "D:\\demo.xlsx";
        EasyExcel.read(filename, DemoData.class, new ExcelListener()).sheet().doRead();
    }

    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("Andy" + i);
            list.add(data);
        }
        return list;
    }
}

class ExcelListener extends AnalysisEventListener<DemoData> {

    private List<DemoData> list = new ArrayList<>();

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println(data);
        list.add(data);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}