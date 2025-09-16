package top.zjk.boot.mp01.controller;

import top.zjk.boot.mp01.common.R;
import top.zjk.boot.mp01.dto.*;
import top.zjk.boot.mp01.service.BookService;
import top.zjk.boot.mp01.vo.BookVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService svc;

    @PostMapping
    public R<BookVO> create(@Valid @RequestBody BookCreateDTO dto) {
        return R.ok(svc.create(dto));
    }

    @PutMapping("/{id}")
    public R<BookVO> update(@PathVariable Long id, @Valid @RequestBody BookUpdateDTO dto) {
        return R.ok(svc.update(id, dto));
    }

    @PatchMapping("/{id}/stock/adjust")
    public R<BookVO> adjust(@PathVariable Long id, @Valid @RequestBody StockAdjustDTO dto) {
        return R.ok(svc.adjustStock(id, dto));
    }

    @GetMapping("/{id}")
    public R<BookVO> get(@PathVariable Long id) {
        return R.ok(svc.get(id));
    }

    @GetMapping("/exists/isbn/{isbn}")
    public R<Map<String,Boolean>> exists(@PathVariable String isbn) {
        return R.ok(Map.of("exists", svc.isbnExists(isbn)));
    }

    @GetMapping("/page")
    public R<?> page(String keyword, String category,
                     @RequestParam(defaultValue = "1") long current,
                     @RequestParam(defaultValue = "10") long size) {
        return R.ok(svc.page(keyword, category, current, size));
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return R.ok();
    }

    @PutMapping("/{id}/restore")
    public R<BookVO> restore(@PathVariable Long id) {
        return R.ok(svc.restore(id));
    }
}