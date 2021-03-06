package kr.co.inogard.enio.api.web;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import java.util.List;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;
import kr.co.inogard.enio.api.commons.constant.RsltCd;
import kr.co.inogard.enio.api.domain.item.Item;
import kr.co.inogard.enio.api.domain.item.ItemDto;
import kr.co.inogard.enio.api.service.item.ItemService;

@RestController
@RequestMapping(value = "/api/v1/items", produces = "application/enio-json;charset=UTF-8")
public class ItemRestController {

  @Autowired
  private ItemService itemService;

  @Autowired
  private ModelMapper modelMapper;

  @RequestMapping(method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  @JsonView(ItemDto.Views.ApiView.class)
  public ItemDto.ResponseWrapper create(@RequestBody @Valid ItemDto.CreateWrapper create) {
    if (CollectionUtils.isEmpty(create.getDatas())) {
      ItemDto.ResponseWrapper res = new ItemDto.ResponseWrapper();
      res.setRsltCd(RsltCd.ERR0000.name());
      res.setRsltMsg("No item data to create");
      return res;
    }

    List<Item> items = itemService.create(create.getDatas());
    List<ItemDto.Response> resItmes = modelMapper.map(items, new TypeToken<List<ItemDto.Response>>() {}.getType());

    ItemDto.ResponseWrapper res = new ItemDto.ResponseWrapper();
    res.setRsltCd(RsltCd.SUC0000.name());
    res.setRsltMsg(RsltCd.SUC0000.getCodeNm());
    res.setDatas(resItmes);

    return res;
  }
}
