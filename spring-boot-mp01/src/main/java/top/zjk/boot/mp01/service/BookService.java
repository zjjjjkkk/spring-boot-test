package top.zjk.boot.mp01.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.zjk.boot.mp01.dto.BookCreateDTO;
import top.zjk.boot.mp01.dto.BookUpdateDTO;
import top.zjk.boot.mp01.dto.StockAdjustDTO;
import top.zjk.boot.mp01.vo.BookVO;

/**
 * @author zjk
 */
public interface BookService {
    BookVO create(BookCreateDTO dto);
    BookVO update(Long id, BookUpdateDTO dto);
    BookVO adjustStock(Long id, StockAdjustDTO dto);
    BookVO get(Long id);
    IPage<BookVO> page(String keyword, String category, long current, long size);
    void delete(Long id);
    BookVO restore(Long id);
    boolean isbnExists(String isbn);
}