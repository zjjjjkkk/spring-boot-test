package top.zjjjjkkk.controller;


import org.springframework.web.bind.annotation.*;
import top.zjjjjkkk.enums.ExpressStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ExpressController {
    @GetMapping("/{status}")
    public String getExpressStatus(@PathVariable ExpressStatus status){
        return "快递当状态：" + status.getLabel();
    }


    @GetMapping("/express/statuses")
    public List<Map<String, String>> getAllStatuses(){
        List<Map<String, String>> list = new ArrayList<>();
        for (ExpressStatus status : ExpressStatus.values()){
            Map<String, String> item = new HashMap<>();
            item.put("value", status.name());
            item.put("label", status.getLabel());
            list.add(item);
        }
        return list;
    }

    @PostMapping("/express/update")
    public String updateExpress(@RequestParam String orderNo, @RequestParam ExpressStatus status){
        return "快递单号" + orderNo + "状态已更新为：" + status.getLabel();
    }
}
