package mybatis.board.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class PageMaker{

    private int totalCount;
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;
    private int displayPageNum = 10;
    private Criteria cri;

    public void setCri(Criteria cri) {
        this.cri = cri;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        calcData();
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public boolean isPrev() {
        return prev;
    }

    public boolean isNext() {
        return next;
    }

    public int getDisplayPageNum() {
        return displayPageNum;
    }

    public Criteria getCri() {
        return cri;
    }

    private void calcData() {
        endPage = (int) (Math.ceil(cri.getPage() / (double)displayPageNum) * displayPageNum);
        System.out.println("cri.getPage : " + cri.getPage());
        System.out.println("displayPageNum : " + displayPageNum);
        System.out.println(endPage);
        startPage = (endPage - displayPageNum) + 1;

        int tempEndPage = (int) (Math.ceil(totalCount / (double)cri.getPerPageNum()));
        if (endPage > tempEndPage) {
            endPage = tempEndPage;
        }
        prev = startPage == 1 ? false : true;
        System.out.println("endPage : " + endPage);
        System.out.println("startPage : "  +startPage);
        System.out.println("cri.getPerPageNum() : " + cri.getPerPageNum());
        System.out.println("totalCount : " + totalCount);
        next = endPage * cri.getPerPageNum() >= totalCount ? false : true;

    }

    public String makeQuery(int page)
    {
        UriComponents uriComponents =
                UriComponentsBuilder.newInstance()
                        .queryParam("page", page)
                        .queryParam("perPageNum", cri.getPerPageNum())
                        .build();

        return uriComponents.toUriString();
    }

    public String makeSearch(int page){

        UriComponents uriComponents =
                UriComponentsBuilder.newInstance()
                .queryParam("page",page)
                .queryParam("perPageNum",cri.getPerPageNum())
                .queryParam("searchType",((SearchCriteria)cri).getSearchType())
                .queryParam("keyword", encoding(((SearchCriteria)cri).getKeyword()))
                .build();
        return uriComponents.toUriString();
    }

    /**
     * 한글 인식 불가능으로 인코딩 필요
     **/
    private String encoding(String keyword){
        if(keyword == null || keyword.trim().length() == 0)
            return "";

        try{
            return URLEncoder.encode(keyword,"UTF-8");
        }catch (UnsupportedEncodingException e){
            return "";
        }
    }

}

