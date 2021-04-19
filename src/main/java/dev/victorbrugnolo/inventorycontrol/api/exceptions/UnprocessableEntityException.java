package dev.victorbrugnolo.inventorycontrol.api.exceptions;

import dev.victorbrugnolo.inventorycontrol.api.dtos.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends APIException {

  private static final long serialVersionUID = 1L;

  public UnprocessableEntityException(final Throwable cause) {
    super(cause);
  }

  public UnprocessableEntityException(final ErrorMessage error) {
    super(error);
  }

  public UnprocessableEntityException(final String error) {
    super(error);
  }

}
