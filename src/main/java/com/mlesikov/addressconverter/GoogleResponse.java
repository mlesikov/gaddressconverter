package com.mlesikov.addressconverter;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 *
 * @author Mihail Lesikov (mlesikov@gmail.com)
 *
 */
public class GoogleResponse {


 private Result[] results ;
 private String status = "";
 @JsonIgnore
 public Object error_message = "";

 public Result[] getResults() {
  return results;
 }
 public void setResults(Result[] results) {
  this.results = results;
 }
 public String getStatus() {
  return status;
 }
 public void setStatus(String status) {
  this.status = status;
 }




}