//(1) 作者姓名及學號. 若參考他人程式改寫, 請加註原創者姓名及學號
//(2) 程式執行時的操作說明：輸入要查詢的日期，格式為yyyy/mm/dd，程式會印出該月的月曆
//(3) 符合的評分標準及自評應得的分數：100分
//1. 程式有意義且可以執行 (+20%)
//2. 完成全部功能 (+80%)
//(4) 其他有利於評分的說明, 例如獨特的功能等：有驗證使用者輸入日期是否正確的功能
import java.util.Scanner;

public class H4_111316034{
    public static boolean isLeapYear(int year){//判斷是否為閏年，參考西元曆法規則
        if((year%4==0 && year%100!=0) || (year%400==0)){//閏年能被4整除但不能被100整除，或能被400整除
            return true;
        }else{
            return false;
        }
    }
    public static int daysInMonth(int year, int month){//回傳每個月確切的天數
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12://31天的月份
                return 31;
            case 4: case 6: case 9: case 11://30天的月份
                return 30;
            case 2:
                return isLeapYear(year)?29:28;//2月根據是否為閏年回傳28或29天
            default:
                throw new AssertionError();//不可能發生的情況，例如月份輸入錯誤，AssertionError是一種在Java中用來表示不應該發生的錯誤情況的異常類型。有查詢網路
        }
    }
    public static void weekday(int year, int month, int day){//計算某月某日是星期幾，並印出該月的月曆
        int firstWeekday = weekdaySakamoto(year, month, 1);//呼叫底下weekdaySakamoto函式，傳入使用者輸入年月份
        for (int i = 0; i < firstWeekday; i++) System.out.print("\t");//先印出前置空格
        int dim = daysInMonth(year, month);//取得該月天數
        for (int i = 1; i <= dim; i++) {//從1號印到該月最後一天
            System.out.print(i + "\t");//印出日期，並以tab對齊，\t可以用tab鍵對齊
            if ((i + firstWeekday) % 7 == 0) System.out.println();//每印到星期六就換行
        }
        System.out.println();//印完月曆後換行
    }
    // Sakamoto 演算法：計算某日期的星期（0=Sunday,1=Monday,...,6=Saturday），有查詢網路https://blog.csdn.net/delepaste/article/details/142185437
    public static int weekdaySakamoto(int y, int m, int d) {
        int[] t = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};//每個月的對應值，因為1月和2月要算作前一年的13月和14月，所以在計算時要特別處理
        int yy = y;//年
        if (m < 3) yy -= 1;//如果是1月或2月，年份減1
        return (yy + yy/4 - yy/100 + yy/400 + t[m-1] + d) % 7;//回傳星期幾，因為是從0開始算所以要%7，(yy + yy/4 - yy/100 + yy/400 + t[m-1] + d) % 7的意思是計算從某個基準日（通常是公元1年1月1日）到指定日期的總天數，然後對7取模，以確定該日期是星期幾。
    }
    public static void printMonthCalendar(int year, int month){//印出某年某月的月曆
        System.out.println("\n      "+year+"年"+month+"月");//先印出使用者輸入的年月份
        System.out.println("日\t一\t二\t三\t四\t五\t六");//印出星期標題，用tab對齊
        weekday(year,month,1);//呼叫weekday函式印出該月月曆
    }
    public static boolean isValidDateWithJavaTime(int y, int m, int d) {//驗證月份是否存在
        if (m < 1 || m > 12) return false;//月份只有一到十二月
        int dim = daysInMonth(y, m);//取得該月天數
        return d >= 1 && d <= dim;//日期必須在1到該月天數之間
    }
    public static void inputDataTrue(int year, int month, int day){//驗證使用者輸入的日期是否正確
        if(!isValidDateWithJavaTime(year, month, day)) {//如果日期不正確
            throw new IllegalArgumentException("日期輸入錯誤，請重新輸入正確的日期");//有查詢網路https://blog.csdn.net/lsoxvxe/article/details/132059996
        }
    }
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("請輸入西元年分/月份/日期（yyyy/mm/dd）:");
                String date = scanner.nextLine().trim();//讀取使用者輸入並去除前後空白，trim()方法會移除字串開頭和結尾的空白字符。
                String[] dateArray = date.split("/"); // 以 "/" 切割字串
                if (dateArray.length != 3) {//如果切割後長度不是3，表示格式錯誤(年/月/日)
                    System.out.println("輸入格式錯誤，正確格式：yyyy/mm/dd");
                    continue;//繼續下一次迴圈，讓使用者重新輸入
                }
                try {
                    int year = Integer.parseInt(dateArray[0]);//年，把使用者輸入從字串轉成整數，parseInt方法會將字串轉換為對應的整數值，dateArray[0]是年，dateArray[1]是月，dateArray[2]是日
                    int month = Integer.parseInt(dateArray[1]);//月
                    int day = Integer.parseInt(dateArray[2]);//日
                    inputDataTrue(year, month, day);//驗證日期是否正確，呼叫inputDataTrue方法
                    printMonthCalendar(year, month);//印出該月月曆
                    break;
                } catch (NumberFormatException nfe) {//捕捉數字格式錯誤例外，nfe表示數字格式錯誤異常的物件
                    System.out.println("數字格式錯誤，請確認輸入為整數。");
                } catch (IllegalArgumentException iae) {//捕捉非法參數例外
                    System.out.println("日期錯誤：" + iae.getMessage());//印出錯誤訊息
                }
            }
        }
    }
    
}