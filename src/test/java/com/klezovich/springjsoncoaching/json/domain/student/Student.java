package com.klezovich.springjsoncoaching.json.domain.student;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
   private String name;
   private String faculty;
   private int averageGrade;

   @JsonValue
   String getStudentSummary() {
       return name + ":" + averageGrade;
   }
}
