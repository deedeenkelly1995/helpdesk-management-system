package com.khadijahkelly.helpdesk.exception;

  
public class TechnicianNotFoundException extends RuntimeException {
    
  public TechnicianNotFoundException(Long id) {
    super("Technician not found with id: " + id);
  }

}
