package jpabook.jpashop_v2.web;

import jpabook.jpashop_v2.domain.item.Book;
import jpabook.jpashop_v2.domain.item.Item;
import jpabook.jpashop_v2.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/items/new")
    public String createForm(Model model){

        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }

    @PostMapping(value = "/items/new")
    public String create(BookForm form){
        Book book = new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        /*
        개선: setter 없애고, create 메서드 이용하여 생성자 이용
         */
        itemService.saveItem(book);
        return "redirect:/items";
    }

    /*
    상품 목록 조회
     */
    @GetMapping(value = "/items")
    public String list(Model model){
        List<Item> items=itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemList";
    }

    /*
    상품 수정 폼
     */
    //수정 전 기존 값 form 형태로 지정
    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(
            @PathVariable("itemId")Long itemId,Model model){
        Book item=(Book) itemService.findOne(itemId); //캐스팅 지양

        BookForm form=new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form",form);
        return "items/updateItemForm";
    }
/*
    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form){

        Book book=new Book();
        book.setId(form.getId());
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }
 */
    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(
            @PathVariable Long itemId, @ModelAttribute("form")BookForm form){
        itemService.updateItem(itemId,form.getName(),form.getPrice(),form.getStockQuantity());
        return "redirect:/items";
    }
}
