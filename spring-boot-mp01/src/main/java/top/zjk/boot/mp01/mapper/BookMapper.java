package top.zjk.boot.mp01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.zjk.boot.mp01.entity.Book;

/**
 * @author zjk
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {
}