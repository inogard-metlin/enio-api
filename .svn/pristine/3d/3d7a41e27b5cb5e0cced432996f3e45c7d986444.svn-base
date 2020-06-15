package kr.co.inogard.enio.api.service.item;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import kr.co.inogard.enio.api.domain.item.Item;
import kr.co.inogard.enio.api.domain.item.ItemDto;
import kr.co.inogard.enio.api.mappers.item.ItemMapper;

@Service
@Transactional
public class ItemService {

  @Autowired
  private ItemMapper itemMapper;

  @Autowired
  private ModelMapper modelMapper;

  public Item create(ItemDto.Create create) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String orgCd = authentication.getName();
    
    Item item = modelMapper.map(create, Item.class);
    item.setOrgCd(orgCd);
    itemMapper.add(item);
    
    // ENIO AGENT 연계전용
    item.setErpItemCd(create.getItemCd()); 

    return item;
  }

  public List<Item> create(List<ItemDto.Create> createList) {
    List<Item> items = new ArrayList<Item>();
    if (!CollectionUtils.isEmpty(createList)) {
      for (ItemDto.Create dto : createList) {
    	  //unitCd가 널이거나 ""일 경우 "-" 추가
    	  if (StringUtils.isEmpty(StringUtils.trimWhitespace(dto.getUnitCd()))) {
    		  dto.setUnitCd("-");
    	  } 
    	  
    	  items.add(this.create(dto));
      }
    }
    return items;
  }

}
