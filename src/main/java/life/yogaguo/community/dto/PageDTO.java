package life.yogaguo.community.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
@ToString
public class PageDTO implements Serializable {
    private List<QuestionDTO> questionList;
    private Boolean isShowPrevious;
    private Boolean isShowFirstPage;
    private Boolean isShowNext;
    private Boolean isShowEndPage;
    private Integer totalPages;
    private Integer page;
    private List<Integer> pageList;
    public void setPage(Integer totalPages, Integer page) {
      this.totalPages = totalPages;
      this.page = page;
      pageList = new ArrayList<>();
      for(int i=0;i <= 3;i++){
          if(page-i>0){
              pageList.add(0,page-i);
          }
          if(page+i<=totalPages){
              pageList.add(page+i);
          }
      }

        /**
         * 是否展示上一页
         */
     if( page == 1){
         this.isShowPrevious = false;
     }else {
         this.isShowPrevious = true;
     }
        /**
         * 是否展示下一页
         */
      if( page == totalPages){
          this.isShowNext = false;
      }else{
          this.isShowNext = true;
      }
        /**
         * 是否展示第一页
         */

        if(pageList.contains(1)){
            this.isShowFirstPage = false;
        }else {
            this.isShowFirstPage = true;
        }
        /**
         * 是否展示最后一页
         */
        if(pageList.contains(totalPages)){
            this.isShowEndPage = false;
        }else {
            this.isShowEndPage = true;
        }
     }
}
