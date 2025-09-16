package top.zjk.boot.mp01.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zjk.boot.mp01.dto.*;
import top.zjk.boot.mp01.entity.Book;
import top.zjk.boot.mp01.Mapper.BookMapper;
import top.zjk.boot.mp01.service.impl.BookService;
import top.zjk.boot.mp01.vo.BookVO;

@Service
@RequiredArgsConstructor
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    private BookVO trans(Book b) {
        return BeanUtil.copyProperties(b, BookVO.class);
    }

    @Override @Transactional
    public BookVO create(BookCreateDTO dto) {
        if (isbnExists(dto.getIsbn())) throw new RuntimeException("ISBN重复");
        Book b = BeanUtil.copyProperties(dto, Book.class);
        this.save(b);
        return trans(b);
    }

    @Override @Transactional
    public BookVO update(Long id, BookUpdateDTO dto) {
        Book b = getById(id);
        if (b == null || b.getDeleted() == 1) throw new RuntimeException("图书不存在");
        BeanUtil.copyProperties(dto, b, "id", "isbn", "stock", "deleted", "version");
        updateById(b);
        return trans(b);
    }

    @Override @Transactional
    public BookVO adjustStock(Long id, StockAdjustDTO dto) {
        Book b = getById(id);
        if (b == null || b.getDeleted() == 1) throw new RuntimeException("图书不存在");
        int newStock = b.getStock() + dto.getAmount();
        if (newStock < 0) throw new RuntimeException("库存不足");
        b.setStock(newStock);
        updateById(b);
        return trans(b);
    }

    @Override
    public BookVO get(Long id) {
        Book b = getById(id);
        if (b == null || b.getDeleted() == 1) throw new RuntimeException("图书不存在");
        return trans(b);
    }

    @Override
    public Page<BookVO> page(String keyword, String category, long current, long size) {
        Page<Book> p = new Page<>(current, size);
        LambdaQueryWrapper<Book> q = new LambdaQueryWrapper<Book>()
                .eq(Book::getDeleted, 0)
                .like(keyword != null, Book::getTitle, keyword)
                .or()
                .like(keyword != null, Book::getAuthor, keyword)
                .eq(category != null, Book::getCategory, category)
                .orderByDesc(Book::getCreateTime);
        return page(p, q).convert(this::trans);
    }

    @Override @Transactional
    public void delete(Long id) {
        if (!removeById(id)) throw new RuntimeException("删除失败");
    }

    @Override @Transactional
    public BookVO restore(Long id) {
        Book b = getById(id);
        if (b == null || b.getDeleted() == 0) throw new RuntimeException("无需恢复");
        b.setDeleted(0);
        updateById(b);
        return trans(b);
    }

    @Override
    public boolean isbnExists(String isbn) {
        return lambdaQuery().eq(Book::getIsbn, isbn).one() != null;
    }
}