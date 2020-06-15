package kr.co.inogard.enio.api.service.dept;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import kr.co.inogard.enio.api.domain.dept.Dept;
import kr.co.inogard.enio.api.domain.dept.DeptDto;
import kr.co.inogard.enio.api.mappers.dept.DeptMapper;

@Service
@Transactional
public class DeptService {

  @Autowired
  private DeptMapper deptMapper;

  public List<DeptDto.ResponseEntry> create(DeptDto.Create create) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String orgCd = authentication.getName();

    List<DeptDto.CreateEntry> createDeptList = create.getDatas();
    List<DeptDto.ResponseEntry> resDeptList = new ArrayList<DeptDto.ResponseEntry>();
    if (!CollectionUtils.isEmpty(createDeptList)) {
      for (DeptDto.CreateEntry createEntry : createDeptList) {
        Dept dept = new Dept();
        dept.setOrgCd(orgCd);
        dept.setDeptNm(createEntry.getDeptNm());
        dept.setDeptTel(createEntry.getDeptTel());

        deptMapper.add(dept);

        DeptDto.ResponseEntry resEntry = new DeptDto.ResponseEntry();
        resEntry.setErpDeptCd(createEntry.getDeptCd());
        resEntry.setE4uDeptCd(dept.getDeptCd());
        resDeptList.add(resEntry);
      }
    }

    return resDeptList;
  }
}
