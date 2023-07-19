package org.example.Controller;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.domain.Book;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books") //请求映射路径
public class BookController {

    @Autowired
    private BookService bookService;

/*    //这样会报错
    @RequestMapping("/save")
    @ResponseBody //返回值类型
   public String save(){
       System.out.println("user save...");
       return  "{'moudle' : 'springmvc'}";
  }*/

    @PostMapping
    public Result save(@RequestBody Book book){
        Boolean flag = bookService.save(book);
        return new Result(flag? Code.POST_OK:Code.POST_ERR, flag);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Boolean flag = bookService.delete(id);
        return new Result(flag? Code.DELETE_OK:Code.DELETE_ERR, flag);
    }


    @PutMapping
    public Result update(@RequestBody Book book) {
        Boolean flag = bookService.update(book);
        return new Result(flag? Code.PUT_OK:Code.PUT_ERR, flag);
    }


    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Book book = bookService.getById(id);
        Integer code = book != null? Code.GET_OK:Code.GET_ERR;
        String msg = book != null? "" : "没有找到对应数据！";
        return new Result(code,book,msg);
    }

    @GetMapping
    public Result getAll() {
        List<Book> book = bookService.getAll();
        for (int i = 0 ; i < book.size(); i++){
            System.out.println(book.get(i));
        }
        Integer code = book != null? Code.GET_OK:Code.GET_ERR;
        String msg = book != null? "" : "没有找到对应数据！";
        return new Result(code, book, msg);
    }
}
