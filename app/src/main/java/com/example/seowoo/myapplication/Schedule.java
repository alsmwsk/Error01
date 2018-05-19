package com.example.seowoo.myapplication;

/**
 * Created by seowoo on 2018-05-19.
 */

public class Schedule {

    private String monday[] = new String[14];
    private String tuesday[] = new String[14];
    private String wednesday[] = new String[14];
    private String thursday[] = new String[14];
    private String friday[] = new String[14];

    public Schedule(){
        for(int i = 0; i < 14; i++)
        {
            monday[i] = "";
            tuesday[i] = "";
            wednesday[i] = "";
            thursday[i] = "";
            friday[i] = "";
        }
    }

    //스케줄 정보를 담는 특정 텍스트가 있을때 이 데이터를 파싱해서 강의 정보가 들어가는 배열에 넣을 수 있도록 하는 것
    //헷갈림
    public void addSchedule(String scheduleText){
        int temp;

        //temp 안에 월이라는 단어가 포함되어 있을 떄 월이라는 단어가 포함된 위치를 반환
        // ex : 월:[3][4][5], 화:[4][5]
        //월이라는 데이터를 기준으로 [3][4][5]에 해당하는 교시 데이터에 강의 정보가 들어갈 수 있도록 한다.
        //indexOf는 0부터 시작한다.
        if((temp = scheduleText.indexOf("월")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    //이해가 잘 안된다.
                    //교시수업
                    monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }

        }

        if((temp = scheduleText.indexOf("화")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    //이해가 잘 안된다.
                    //교시수업
                    tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }

        }

        if((temp = scheduleText.indexOf("수")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    //이해가 잘 안된다.
                    //교시수업
                    wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }

        }

        if((temp = scheduleText.indexOf("목")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    //이해가 잘 안된다.
                    //교시수업
                    thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }

        }

        if((temp = scheduleText.indexOf("금")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    //이해가 잘 안된다.
                    //교시수업
                    friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }

        }

    }

    //새롭게 추가할려는 수강신청의 날짜데이터가 현재 자리잡고 있는 스케쥴 데이터에 중복되지 안흔지 확인

    public boolean validate(String scheduleText){
        if (scheduleText.equals(""))
        {
            return true;
        }
        int temp;
        if((temp = scheduleText.indexOf("월")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    //이해가 잘 안된다.
                    //공백이 아니라면 어떤 데이터가 들어가 있다는 소리
                    if(!monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        return false;
                    }
                }
            }

        }

        if((temp = scheduleText.indexOf("금")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    //이해가 잘 안된다.
                    //공백이 아니라면 어떤 데이터가 들어가 있다는 소리
                    if(!tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        return false;
                    }
                }
            }

        }

        if((temp = scheduleText.indexOf("금")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    //이해가 잘 안된다.
                    //공백이 아니라면 어떤 데이터가 들어가 있다는 소리
                    if(!wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        return false;
                    }
                }
            }

        }

        if((temp = scheduleText.indexOf("금")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    //이해가 잘 안된다.
                    //공백이 아니라면 어떤 데이터가 들어가 있다는 소리
                    if(!thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        return false;
                    }
                }
            }

        }

        if((temp = scheduleText.indexOf("금")) > -1)
        {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;

            for(int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++)
            {
                if(scheduleText.charAt(i) == '[')
                {
                    startPoint = i;
                }
                if(scheduleText.charAt(i) == ']')
                {
                    endPoint = i;
                    //이해가 잘 안된다.
                    //공백이 아니라면 어떤 데이터가 들어가 있다는 소리
                    if(!friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals(""))
                    {
                        return false;
                    }
                }
            }

        }

        return true;

    }
}
