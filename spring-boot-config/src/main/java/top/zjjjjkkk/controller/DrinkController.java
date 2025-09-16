package top.zjjjjkkk.controller;

import top.zjjjjkkk.enums.DrinkType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/drink")
public class DrinkController {

    @GetMapping("/list")
    public List<Map<String, Object>> listAll() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DrinkType dt : DrinkType.values()) {
            Map<String, Object> item = new HashMap<>();
            item.put("value", dt.name());
            item.put("label", dt.getLabel());
            item.put("price", dt.getPrice());
            list.add(item);
        }
        return list;
    }

    @GetMapping("/price/{type}")
    public double getPrice(@PathVariable DrinkType type) {
        return type.getPrice();
    }
}