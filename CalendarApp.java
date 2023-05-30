package swingshow;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CalendarApp {
    public static void main(String[] args) {
        
    	
    	//创建窗口
    	JFrame frame = new JFrame("MyCalendarApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel calendarPanel = new JPanel(new GridLayout(0, 7));

        
        //Calendar类获取时间
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        
        // 创建年月标签
        JComboBox<String> yearcb = new JComboBox<>();
        for (int i = 1900; i <= 2100; i++) {
            yearcb.addItem(""+i);
        }
        yearcb.setSelectedIndex(year - 1900);

        JComboBox<String> monthcb = new JComboBox<>();
        String[] months = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
        for (String m : months) {
            monthcb.addItem(m);
        }
        monthcb.setSelectedIndex(month);

        
        //创建查询按钮,设置监听
        JButton findButton = new JButton("查询");
        findButton.addActionListener(event -> {
            String selectedYear = yearcb.getSelectedItem().toString();
            String selectedMonth = monthcb.getSelectedItem().toString();
            cal.set(Calendar.YEAR, Integer.parseInt(selectedYear));
            cal.set(Calendar.MONTH, monthcb.getSelectedIndex());
            setCalendar(cal, calendarPanel);
        });
 
        // 添加标签和下拉框到上方
        JLabel dateLabel = new JLabel();
        JLabel timeLabel = new JLabel();      
        JPanel northPanel = new JPanel();
        northPanel.add(yearcb);
        northPanel.add(monthcb);
        northPanel.add(findButton);
        northPanel.add(dateLabel);
        northPanel.add(timeLabel);
        frame.add(northPanel, BorderLayout.NORTH);

        // 添加日历组件到中心
        setCalendar(cal, calendarPanel);
        frame.add(calendarPanel, BorderLayout.CENTER);

        
        // 创建定时器每秒更新时间
        Timer timer = new Timer(1000, event -> {
            Calendar now = Calendar.getInstance();
            Date date = now.getTime();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            timeLabel.setText("时间:" + timeFormat.format(date));
            dateLabel.setText("日期:" + now.get(Calendar.YEAR)+"年"+now.get(Calendar.MONTH)+"月"+now.get(Calendar.DATE)+"日");
        });
        timer.setInitialDelay(0);
        timer.start();

        frame.setPreferredSize(new Dimension(500, 300));
        frame.pack();
        frame.setVisible(true);
    }

 
    //日历基本显示
    private static void setCalendar(Calendar cal, JPanel calendarPanel) {
        calendarPanel.removeAll();

        // 添加周几标签
        String[] week = {"日", "一", "二", "三", "四", "五", "六"};
        for (String s : week) {
            calendarPanel.add(new JLabel(s));
        }

        cal.set(Calendar.DATE, 1);
        int firstDayInMonth = cal.get(Calendar.DAY_OF_WEEK);
        int numberOfDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 添加日历日期
        for (int day = 1; day <= numberOfDaysInMonth; day++) {
            if (day == 1) {
                for (int i = 1; i < firstDayInMonth; i++) {
                    calendarPanel.add(new JLabel(""));
                }
            }
            calendarPanel.add(new JLabel(String.valueOf(day)));
        }
    }
}