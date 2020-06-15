package kr.co.inogard.enio.api.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import kr.co.inogard.enio.api.commons.constant.EvtIOType;
import kr.co.inogard.enio.api.commons.constant.EvtSt;
import kr.co.inogard.enio.api.mappers.agent.AgentMapper;

@Controller
@RequestMapping("/events")
public class EventController {

  @Autowired
  AgentMapper agentMapper;

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView getEvents() {
    ModelAndView model = new ModelAndView("events/list");
    model.addObject("agents", agentMapper.findAll());
    model.addObject("evtIOTypes", EvtIOType.values());
    model.addObject("evtSts", EvtSt.values());
    return model;
  }
}