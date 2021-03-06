package kr.co.inogard.enio.api.web;

import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.inogard.enio.api.commons.constant.RsltCd;
import kr.co.inogard.enio.api.domain.pr.Pr;
import kr.co.inogard.enio.api.domain.pr.PrDto;
import kr.co.inogard.enio.api.service.pr.PrService;

@RestController
@RequestMapping(value = "/api/v1/pr", produces = "application/enio-json;charset=UTF-8")
public class PrRestController {

	@Autowired
	private PrService prService;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	@JsonView(PrDto.Views.ApiView.class)
	public PrDto.Response create(@RequestPart("data") @Valid PrDto.Create create, 
												 @RequestPart(name = "files", required = false) List<MultipartFile> files) {
		Pr pr = prService.create(create, files);

		PrDto.Response res = modelMapper.map(pr, PrDto.Response.class);
		res.setErpPrNo(create.getPrNo());
		res.setPrNo(pr.getPrNo());		
		res.setRsltCd(RsltCd.SUC0000.name());
		res.setRsltMsg(RsltCd.SUC0000.getCodeNm());

		return res;
	}

	@RequestMapping(value = "/cancel/{prNo}", method = PATCH)
	@ResponseStatus(HttpStatus.OK)
	public PrDto.ReqCancelResponse cancel(@PathVariable("prNo") String prNo,
			@RequestBody @Valid PrDto.ReqCancelCreate cre) throws IOException {
		PrDto.ReqCancelResponse res = prService.cancel(cre);
		return res;
	}
}
