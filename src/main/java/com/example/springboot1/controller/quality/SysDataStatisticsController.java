package com.example.springboot1.controller.quality;

import com.example.springboot1.common.browser.core.controller.BaseController;
import com.example.springboot1.common.browser.core.domain.AjaxResult;
import com.example.springboot1.common.browser.core.page.TableDataInfo;
import com.example.springboot1.entity.browser.quality.SysBatch;
import com.example.springboot1.entity.browser.quality.SysDefectType;
import com.example.springboot1.entity.browser.quality.SysPart;
import com.example.springboot1.service.ISysBatchService;
import com.example.springboot1.service.ISysDefectTypeService;
import com.example.springboot1.service.ISysPartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 授予数据展示的控制层
 */
@RestController
@RequestMapping("/quality/statistic")
public class SysDataStatisticsController extends BaseController {
    @Autowired
    private ISysPartService partService;

    @Autowired
    private ISysDefectTypeService defectTypeService;

    @Autowired
    private ISysBatchService batchService;


    /**
     * 查询某个设备的检测零件
     */
    @ApiOperation(value = "查询设备的检测零件")
    @GetMapping("/list")
    public TableDataInfo list(SysPart part){
        startPage();
        List<SysPart> list = partService.selectPartIds(part);
        return getDataTable(list);
    }

    @ApiOperation(value = "查询批次的合格信息")
    @GetMapping("/getBatchInfo")
    public TableDataInfo batchList(SysBatch batch){

        List<SysBatch> list = batchService.selectBatchDetectedList(batch);
        return getDataTable(list);
    }

    @ApiOperation(value = "统计数据获取")
    @GetMapping("/getStatisticInfo")
    public AjaxResult getDefectStatisticInfo(SysPart part)
    {
        SysDefectType defectType = new SysDefectType();
        List<SysDefectType> selectDefectTypeList = defectTypeService.selectDefectTypeList(defectType);

        ArrayList<ArrayList<Object>> list = new ArrayList<>();

        ArrayList<String> Time = getWeekList(part.getParams());

        for (SysDefectType  sysDefectType : selectDefectTypeList){
            if (!sysDefectType.getDefectDescription().equals("正常")){
                ArrayList<Object> temp = new ArrayList<>();
                temp.add(sysDefectType.getDefectDescription());

                // 查询该缺陷的数量
                part.setDefectTypeName(sysDefectType.getDefectDescription());
                for (String time : Time){
                    // 将时间参数导入part
                    HashMap<String, Object> params = new HashMap<>();
                    params.put("beginTime", time);
                    params.put("endTime", time);
                    part.setParams(params);

                    temp.add(partService.statisticPart(part));
                }
                list.add(temp);
            }
        }
        AjaxResult ajax = AjaxResult.success(list);
        return ajax;
    }


    private ArrayList<String> getWeekList(Map<String,Object> timeMap){

        String beginTime = timeMap.get("beginTime").toString();
        String endTime = timeMap.get("endTime").toString();

        ArrayList<String> time = new ArrayList<>();
        // 将开始时间和结束时间转换为 LocalDate 对象
        LocalDate startDate = LocalDate.parse(beginTime);
        LocalDate endDate = LocalDate.parse(endTime);

        // 从开始时间迭代到结束时间，并将每个日期转换为字符串并添加到 time 列表中
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            time.add(currentDate.toString());
            currentDate = currentDate.plusDays(1);
        }
        return time;
    }




}
