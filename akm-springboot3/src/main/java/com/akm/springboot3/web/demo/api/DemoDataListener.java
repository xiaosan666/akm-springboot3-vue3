package com.akm.springboot3.web.demo.api;

import com.akm.springboot3.web.demo.entity.DemoUser;
import com.akm.springboot3.web.demo.service.DemoUserService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class DemoDataListener extends AnalysisEventListener<DemoUser> {

    /**
     * 每隔500条存储数据库
     */
    private static final int BATCH_COUNT = 500;
    private final DemoUserService demoUserService;
    private final List<DemoUser> list = new ArrayList<>();

    public DemoDataListener(DemoUserService service) {
        this.demoUserService = service;
    }

    @Override
    public void invoke(DemoUser data, AnalysisContext analysisContext) {
        data.setRowNum(analysisContext.readSheetHolder().getRowIndex() + 1);
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        demoUserService.batchInsert(list);
        log.info("所有数据解析完成！");
    }

    /**
     * 存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        demoUserService.batchInsert(list);
        log.info("存储数据库成功！");
    }
}
