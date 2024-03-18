package com.example.springboot1.controller.quality;

import com.example.springboot1.common.browser.core.controller.BaseController;
import com.example.springboot1.common.browser.core.domain.AjaxResult;
import com.example.springboot1.common.browser.core.page.TableDataInfo;
import com.example.springboot1.entity.browser.quality.SysDefectType;
import com.example.springboot1.entity.browser.quality.SysPart;
import com.example.springboot1.service.ISysDefectTypeService;
import com.example.springboot1.service.ISysPartService;
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


    /**
     * 查询某个设备的检测零件
     */
    @GetMapping("/list")
    public TableDataInfo list(SysPart part){
        startPage();
        List<SysPart> list = partService.selectPartIds(part);
        return getDataTable(list);
    }

    @GetMapping("/getStatisticInfo")
    public AjaxResult getDefectStatisticInfo(SysPart part)
    {
        SysDefectType defectType = new SysDefectType();
        List<SysDefectType> selectDefectTypeList = defectTypeService.selectDefectTypeList(defectType);

        ArrayList<ArrayList<Object>> list = new ArrayList<>();

        ArrayList<String> weekTime = getWeekList();

        for (SysDefectType  sysDefectType : selectDefectTypeList){
            if (!sysDefectType.getDefectDescription().equals("正常")){
                ArrayList<Object> temp = new ArrayList<>();
                temp.add(sysDefectType.getDefectTypeName());

                // 查询该缺陷的数量
                part.setDefectTypeName(sysDefectType.getDefectDescription());
                for (String time : weekTime){
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


    private ArrayList<String> getWeekList(){
        ArrayList<String> time = new ArrayList<>();

        // 获取本周的周一日期
        LocalDate currentDate = LocalDate.now();
        LocalDate monday = currentDate.with(DayOfWeek.MONDAY);

        // 格式化日期并添加到列表中
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (!monday.isAfter(currentDate)) {
            String formattedDate = monday.format(formatter);
            time.add(formattedDate);
            monday = monday.plusDays(1);
        }
        return time;
    }




}
