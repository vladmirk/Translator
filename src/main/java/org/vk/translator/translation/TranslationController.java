package org.vk.translator.translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.vk.translator.ClientParams;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TranslationController {

  private final TranslationService service;

  @Autowired
  public TranslationController(TranslationService service) {
    this.service = service;
  }

  @PostMapping(value = "/translate", consumes = "application/json")
  public TranslationResponse translateByWords(@RequestBody TranslationParam param, HttpServletRequest request) {
    Translation translation = service.translate(param, new ClientParams(getClientIp(request)));
    return new TranslationResponse(translation.getTranslation(), translation.getCopyright());
  }

  private String getClientIp(HttpServletRequest request) {
    String address = "";
    if (request != null) {
      address = request.getHeader("X-FORWARDED-FOR");
      if (address == null || "".equals(address)) {
        address = request.getRemoteAddr();
      }
    }
    return address;
  }

}
