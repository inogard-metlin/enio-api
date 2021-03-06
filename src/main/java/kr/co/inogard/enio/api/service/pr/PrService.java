package kr.co.inogard.enio.api.service.pr;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.inogard.enio.api.commons.constant.CnclFlag;
import kr.co.inogard.enio.api.commons.constant.DocTypeCd;
import kr.co.inogard.enio.api.commons.constant.RsltCd;
import kr.co.inogard.enio.api.commons.exception.EnioRunTimeException;
import kr.co.inogard.enio.api.commons.handler.EnioFileHandler;
import kr.co.inogard.enio.api.domain.file.CmmFile;
import kr.co.inogard.enio.api.domain.ftp.FtpFileDto;
import kr.co.inogard.enio.api.domain.pr.Pr;
import kr.co.inogard.enio.api.domain.pr.PrDto;
import kr.co.inogard.enio.api.domain.pr.PrFile;
import kr.co.inogard.enio.api.domain.pr.PrItem;
import kr.co.inogard.enio.api.domain.pr.PrMap;
import kr.co.inogard.enio.api.domain.pr.PrSrv;
import kr.co.inogard.enio.api.domain.rfq.Rfq;
import kr.co.inogard.enio.api.mappers.file.FileMapper;
import kr.co.inogard.enio.api.mappers.pr.PrFileMapper;
import kr.co.inogard.enio.api.mappers.pr.PrItemMapper;
import kr.co.inogard.enio.api.mappers.pr.PrMapMapper;
import kr.co.inogard.enio.api.mappers.pr.PrMapper;
import kr.co.inogard.enio.api.mappers.pr.PrSrvMapper;
import kr.co.inogard.enio.api.mappers.rfq.RfqMapper;
import kr.co.inogard.enio.api.service.ftp.FtpService;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PrService {

	@Autowired
	private PrMapper prMapper;

	@Autowired
	private PrItemMapper prItemMapper;

	@Autowired
	private PrSrvMapper prSrvMapper;

	@Autowired
	private PrFileMapper prFileMapper;

	@Autowired
	private PrMapMapper prMapMapper;

	@Autowired
	private FileMapper fileMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EnioFileHandler enioFileHandler;

	@Autowired
	private FtpService ftpService;

	@Autowired
	private RfqMapper rfqMapper;

	public Pr create(PrDto.Create create, List<MultipartFile> files) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String orgCd = authentication.getName();

		Pr pr = modelMapper.map(create, Pr.class);
		pr.setOrgCd(orgCd);
		
		if(null == pr.getSubOrgCd() || "".equals(pr.getSubOrgCd())) {
			pr.setSubOrgCd(orgCd + "01");
		}

		List<PrItem> prItemList = modelMapper.map(create.getItemList(), new TypeToken<List<PrItem>>() {}.getType());

		prMapper.add(pr);
		log.info("prNo : {}", pr.getPrNo());
		log.info("subOrgCd : {}", pr.getSubOrgCd());
		
		if (!CollectionUtils.isEmpty(prItemList)) {
			for (PrItem prItem : prItemList) {
				prItem.setPrNo(pr.getPrNo());

				//unitCd가 널이거나 ""일 경우 "-" 추가
				if (StringUtils.isEmpty(StringUtils.trimWhitespace(prItem.getUnitCd()))) {
					prItem.setUnitCd("-");
				}
				
				if (!pr.getPrTypecd().equals("N")) {
					prItem.setDlvLoc("");

					prItem.setConstrLoc(pr.getDlvLoc());
					switch(pr.getConstrPeriodInptKind().toUpperCase()) {
						case "DATE":
							prItem.setConstrFrDt(pr.getDlvFrYmd());
							prItem.setConstrToDt(pr.getDlvToYmd());
							prItem.setConstrPeriodInptKind(pr.getConstrPeriodInptKind());
							prItem.setConstrPeriodRmrk("");
							break;
						case "TEXT":
							prItem.setConstrFrDt(null);
							prItem.setConstrToDt(null);
							prItem.setConstrPeriodInptKind(pr.getConstrPeriodInptKind());
							prItem.setConstrPeriodRmrk(pr.getConstrPeriodRmrk());							
					}
					
					// 공사(S), 용역(W) 시 prservice 등록
					List<PrSrv> prSrvList = modelMapper.map(prItem.getSrvList(), new TypeToken<List<PrSrv>>() { }.getType());

					if (!CollectionUtils.isEmpty(prSrvList)) {
						for (PrSrv prSrv : prSrvList) {
							prSrv.setPrNo(pr.getPrNo());
							prSrvMapper.add(prSrv);
						}
					}

				}

				prItemMapper.add(prItem);

			}
		}

		if (!CollectionUtils.isEmpty(files)) {
			File ctmTempDir = enioFileHandler.createTempDirectory();
			
			try {
				List<FtpFileDto.Store> storeList = new ArrayList<FtpFileDto.Store>();
				
				for (MultipartFile multipartFile : files) {
					log.debug("multipartFile name : {}", multipartFile.getName());
					log.debug("multipartFile originalFilename : {}",
							MimeUtility.decodeText(multipartFile.getOriginalFilename()));

					File file = new File(ctmTempDir, MimeUtility.decodeText(multipartFile.getOriginalFilename()));
					multipartFile.transferTo(file);

					CmmFile cmmFile = new CmmFile();
					cmmFile.setCliFilenm(file.getName());
					cmmFile.setFileType(DocTypeCd.CD_10.getCodeNo());
					cmmFile.setFileSz(new BigDecimal(file.length()));
					cmmFile.setRegUsrcd(pr.getPrChrgrCd());
					fileMapper.add(cmmFile);

					PrFile prFile = new PrFile();
					prFile.setPrNo(pr.getPrNo());
					prFile.setFileNo(cmmFile.getFileNo());
					prFileMapper.add(prFile);

					FtpFileDto.Store store = new FtpFileDto.Store();
					store.setFile(file);
					store.setRemoteFileName(cmmFile.getSvrFilenm());
					store.setRemoteFilePath(cmmFile.getFileDir());
					storeList.add(store);
				}
				
				ftpService.store(storeList);
				
			} catch (Exception e) {
				throw new EnioRunTimeException(e);
			} finally {
				enioFileHandler.cleanUpDirectory(ctmTempDir);
			}
		}

		PrMap prMap = new PrMap();
		prMap.setAgtCd(pr.getOrgCd());
		prMap.setE4uPrNo(pr.getPrNo());
		prMap.setErpPrNo(create.getPrNo());
		prMapMapper.add(prMap);

		// 입찰초청서 바로생성
		Map<String, Object> prInfo = new HashMap<String, Object>();
		prInfo.put("prNo", pr.getPrNo());

		String bidSubTypecd = pr.getBidSubTypecd();
		String bidTypecd = pr.getBidTypecd();
		String selectedBidderStd = pr.getSelectedBidderStd();
		BigDecimal bidderRate = pr.getBidderRate();
		Date bidExpireDt = pr.getBidExpireDt();
		String rfqdocYn = pr.getRfqdocYn();
		Date rfqdocExpireDt = pr.getRfqdocExpireDt();
		String rfqdocRmrk = pr.getRfqdocRmrk();
		String techdocYn = pr.getTechdocYn();
		Date techdocExpireDt = pr.getTechdocExpireDt();
		String techdocRmrk = pr.getTechdocRmrk();
		String constrPeriodInptKind = pr.getConstrPeriodInptKind();
		String constrPeriodRmrk = pr.getConstrPeriodRmrk();			

		prInfo.put("bidSubTypecd", ((bidSubTypecd == null || bidSubTypecd.trim().length() == 0) ? "4" : bidSubTypecd));
		prInfo.put("bidTypecd", ((bidTypecd == null || bidTypecd.trim().length() == 0) ? "1" : bidTypecd));
		prInfo.put("selectedBidderStd", selectedBidderStd);
		prInfo.put("bidderRate", bidderRate);
		prInfo.put("bidExpireDt", bidExpireDt);
		prInfo.put("rfqdocYn", rfqdocYn);
		prInfo.put("rfqdocExpireDt", rfqdocExpireDt);
		prInfo.put("rfqdocRmrk", rfqdocRmrk);
		prInfo.put("techdocYn", techdocYn);
		prInfo.put("techdocExpireDt", techdocExpireDt);
		prInfo.put("techdocRmrk", techdocRmrk);
		prInfo.put("constrPeriodInptKind", constrPeriodInptKind);
		prInfo.put("constrPeriodRmrk", constrPeriodRmrk);

		String newRfqNo = rfqMapper.findNewRfqNoAfterCreateFromPr(prInfo);
		log.info("newRfqNo : {}", newRfqNo);

		return prMapper.findByPrNo(pr.getPrNo());
	}

	public Pr getPr(String prNo) {
		Pr pr = prMapper.findByPrNo(prNo);
		List<PrItem> prItemList = prItemMapper.findAllByPrNo(prNo);
		pr.setItemList(prItemList);
		for (PrItem prItem : prItemList) {
			Map<String, String> srchMap = new HashMap<String, String>();
			srchMap.put("prNo", prNo);
			srchMap.put("itemSeq", prItem.getItemSeq());
			List<PrSrv> srvList = prSrvMapper.findAll(srchMap);
			prItem.setSrvList(srvList);
		}
		List<PrFile> fileList = prFileMapper.findAllByPrNo(prNo);
		pr.setFileList(fileList);

		return pr;
	}

	public PrDto.ReqCancelResponse cancel(PrDto.ReqCancelCreate cre) {
		String prNo = cre.getPrNo();

		PrDto.ReqCancelResponse res = new PrDto.ReqCancelResponse();
		res.setRsltCd(RsltCd.SUC0000.name());
		res.setRsltMsg(RsltCd.SUC0000.getCodeNm());
		res.setPrNo(cre.getPrNo());
		res.setCnclFlag(CnclFlag.CC.name());
		res.setCnclReqDt(cre.getCnclReqDt());
		res.setCnclRsltDt(new Date());

		Pr pr = prMapper.findByPrNo(prNo);
		if (pr == null) {
			res.setCnclFlag(CnclFlag.CF.name());
			res.setCnclRsltMsg("구매취소(자료없음)");
			return res;
		}

		List<PrItem> prItemList = prItemMapper.findAllByPrNo(prNo);
		PrItem firstPrItem = prItemList.get(0);
		String rfqNo = firstPrItem.getRfqNo();
		Rfq rfq = rfqMapper.findByRfqNo(rfqNo);

		if (rfq == null) {
			res.setCnclFlag(CnclFlag.CF.name());
			res.setCnclRsltMsg("구매취소(이미처리됨)");
			return res;
		}

		if ("00".equals(rfq.getRfqSt())) {
			Map<String, Object> info = new HashMap<String, Object>();
			info.put("prNo", prNo);
			info.put("rfqNo", rfqNo);
			Map<String, Object> cnclRsltInfo = prMapper.findCancelResultAfterCancel(info);
			res.setCnclFlag(CnclFlag.CC.name());
			res.setCnclRsltDt((Date) cnclRsltInfo.get("returnDt"));
			res.setCnclRsltMsg((String) cnclRsltInfo.get("returnRmrk"));
		} else {
			res.setCnclFlag(CnclFlag.CF.name());
			res.setCnclRsltDt(new Date());
			res.setCnclRsltMsg("입찰진행에 따른 취소불가");
		}

		return res;
	}
}
