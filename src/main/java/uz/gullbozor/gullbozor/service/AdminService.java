package uz.gullbozor.gullbozor.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.dto.AdminBodyDto;
import uz.gullbozor.gullbozor.dto.AdminBodyDto2;
import uz.gullbozor.gullbozor.entity.AdminBody;
import uz.gullbozor.gullbozor.entity.AdminHeadOne;
import uz.gullbozor.gullbozor.entity.AdminHeadTwo;
import uz.gullbozor.gullbozor.repository.AdminBodyRepo;
import uz.gullbozor.gullbozor.repository.AdminHeadOneRepo;
import uz.gullbozor.gullbozor.repository.AdminHeadTwoRepo;
import uz.gullbozor.gullbozor.repository.NewComRepo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminHeadOneRepo adminHeadOneRepo;

    @Autowired
    private AdminHeadTwoRepo adminHeadTwoRepo;

    @Autowired
    private AdminBodyRepo adminBodyRepo;


    Date dNow = new Date( );

    SimpleDateFormat monthF = new SimpleDateFormat ("MM");
    byte month = Byte.parseByte(monthF.format(dNow));

    SimpleDateFormat monthName = new SimpleDateFormat ("MMMM");              // Sanalarni olish
    String monthNames = monthName.format(dNow);

    SimpleDateFormat yearF = new SimpleDateFormat ("yyyy");
    short year = Short.parseShort((yearF.format(dNow)));

    SimpleDateFormat dayOfMonth = new SimpleDateFormat ("d");
    byte dayofMonth = Byte.parseByte((dayOfMonth.format(dNow)));
    @Autowired
    private NewComRepo newComRepo;


    public void addHeadOneTest(Byte num, Byte testDay, Integer testPv, Byte testMonth, Short testYear){
        if (adminHeadOneRepo.existsByYearAndMonth(testYear, testMonth)) {

            Optional<AdminHeadOne> OptionalbyYearAndMonth = adminHeadOneRepo.findByYearAndMonth(testYear, testMonth);
            AdminHeadOne adminHeadOne = OptionalbyYearAndMonth.get();



            adminHeadOne.setBuys(Long.valueOf(testPv));
            adminHeadOne.setCalls(Long.valueOf(testPv));
            adminHeadOne.setVisitors(Long.valueOf(testPv));



            adminHeadOneRepo.save(adminHeadOne);


        }else {

            AdminHeadOne adminHeadOne = new AdminHeadOne();
            adminHeadOne.setYear(testYear);
            adminHeadOne.setMonthName(monthNames);
            adminHeadOne.setMonth(testMonth);
            adminHeadOneRepo.save(adminHeadOne);
            addHeadOne(num);
        }
    }


    public void addHeadTwoTest(Byte num, Byte testDay, Integer testPv, Byte testMonth, Short testYear){
        if (adminHeadTwoRepo.existsByYearAndMonth(testYear, testMonth)) {

            Optional<AdminHeadTwo> OptionalbyYearAndMonth = adminHeadTwoRepo.findByYearAndMonth(testYear, testMonth);

            AdminHeadTwo adminHeadTwo = OptionalbyYearAndMonth.get();

            adminHeadTwo.setExits(Long.valueOf(testPv));
            adminHeadTwo.setPaid(Long.valueOf(testPv));
            adminHeadTwo.setTrial(Long.valueOf(testPv));

            adminHeadTwoRepo.save(adminHeadTwo);

        }else {

            AdminHeadTwo adminHeadTwo = new AdminHeadTwo();
            adminHeadTwo.setYear(testYear);
            adminHeadTwo.setMonthName(monthNames);
            adminHeadTwo.setMonth(testMonth);
            adminHeadTwoRepo.save(adminHeadTwo);
            addHeadOne(num);
        }
    }
    public void addBodyTest(Byte num, Byte testDay, Integer testPv, Byte testMonth, Short testYear) {

        if (!adminBodyRepo.existsByYearAndMonthAndTypeNumberAndDay(testYear,testMonth,num,testDay)) {

            AdminBody adminBody = new AdminBody();
            adminBody.setYear(testYear);
            adminBody.setMonth(testMonth);
            adminBody.setTypeNumber(num);
            adminBody.setDay(testDay);
            adminBody.setPv(testPv);
            adminBodyRepo.save(adminBody);

        }









    }

    public void addBody(Byte num) {

        if (adminBodyRepo.existsByYearAndMonthAndTypeNumberAndDay(year,month,num,dayofMonth)) {

            Optional<AdminBody> optionalAdminBody = adminBodyRepo.findByYearAndMonthAndTypeNumberAndDay(year, month, num,dayofMonth);
            AdminBody adminBody = optionalAdminBody.get();

            Integer pv = adminBody.getPv();
            pv++;
            adminBody.setPv(pv);
            adminBodyRepo.save(adminBody);


        } else {
            AdminBody adminBody = new AdminBody();
            adminBody.setYear(year);
            adminBody.setMonth(month);
            adminBody.setTypeNumber(num);
            adminBody.setDay(dayofMonth);
            adminBody.setPv(0);
            adminBodyRepo.save(adminBody);
            addBody(num);
        }



    }

    public AdminBodyDto2 getBody(Byte typeNumber, Short year, Byte month) {
        AdminBodyDto2 adminBodyDto2 = new AdminBodyDto2();
        List<AdminBodyDto> adminBodyDtoList = new ArrayList<>();

        Integer max = 0;

        for (AdminBody adminBody : adminBodyRepo.findAllByYearAndMonthAndTypeNumber(year, month, typeNumber)) {

            AdminBodyDto adminBodyDto = new AdminBodyDto();

            adminBodyDto.setDay(adminBody.getDay());
            adminBodyDto.setPv(adminBody.getPv());
            adminBodyDtoList.add(adminBodyDto);
            if ( adminBody.getPv() > max ) max = adminBody.getPv();

        }


        adminBodyDto2.setAdminBodyDtoList(adminBodyDtoList);
        adminBodyDto2.setMax(max);

        return adminBodyDto2;


    }

    public AdminBodyDto2 getBodyDefault(Byte typeNumber) {

        AdminBodyDto2 adminBodyDto2 = new AdminBodyDto2();
        List<AdminBodyDto> adminBodyDtoList = new ArrayList<>();


        Integer max = 0;

        for (AdminBody adminBody : adminBodyRepo.findAllByYearAndMonthAndTypeNumber(year, month, typeNumber)) {

            AdminBodyDto adminBodyDto = new AdminBodyDto();

            adminBodyDto.setDay(adminBody.getDay());
            adminBodyDto.setPv(adminBody.getPv());

            adminBodyDtoList.add(adminBodyDto);
            if ( adminBody.getPv() > max ) max = adminBody.getPv();

        }


        adminBodyDto2.setAdminBodyDtoList(adminBodyDtoList);
        adminBodyDto2.setMax(max);

        return adminBodyDto2;

    }

    public void addHeadOne(Byte num) {

        if (adminHeadOneRepo.existsByYearAndMonth(year, month)) {

            Optional<AdminHeadOne> OptionalbyYearAndMonth = adminHeadOneRepo.findByYearAndMonth(year, month);
            AdminHeadOne adminHeadOne = OptionalbyYearAndMonth.get();

            switch (num) {

                case 1:

                    Long calls = adminHeadOne.getCalls();
                    if (calls == null) calls = 0L;
                    calls++;
                    adminHeadOne.setCalls(calls);

                    break;

                case 2:
                    Long buys = adminHeadOne.getBuys();
                    if (buys == null) buys = 0L;
                    buys++;
                    adminHeadOne.setBuys(buys);
                    break;

                case 3:
                    Long visitors = adminHeadOne.getVisitors();
                    if (visitors == null) visitors = 0L;
                    visitors++;
                    adminHeadOne.setVisitors(visitors);
                    break;

                default: break;

            }
            adminHeadOneRepo.save(adminHeadOne);


        }else {

            AdminHeadOne adminHeadOne = new AdminHeadOne();
            adminHeadOne.setYear(year);
            adminHeadOne.setMonthName(monthNames);
            adminHeadOne.setMonth(month);
            adminHeadOneRepo.save(adminHeadOne);
            addHeadOne(num);
        }



    }

    public void addHeadTwo(Byte num) {

        if (adminHeadTwoRepo.existsByYearAndMonth(year, month)) {

            Optional<AdminHeadTwo> OptionalbyYearAndMonth = adminHeadTwoRepo.findByYearAndMonth(year, month);

            AdminHeadTwo adminHeadTwo = OptionalbyYearAndMonth.get();

            switch (num) {

                case 4:
                    Long trial = adminHeadTwo.getTrial();
                    if (trial == null) trial = 0L;
                    trial++;
                    adminHeadTwo.setTrial(trial);
                    break;

                case 5:
                    Long paid = adminHeadTwo.getPaid();
                    if (paid == null) paid = 0L;
                    paid++;
                    adminHeadTwo.setPaid(paid);
                    break;

                case 6:
                    Long exits = adminHeadTwo.getExits();
                    if (exits == null) exits = 0L;
                    exits++;
                    adminHeadTwo.setExits(exits);
                    break;

                default: break;
            }

            adminHeadTwoRepo.save(adminHeadTwo);

        }else {

            AdminHeadTwo adminHeadTwo = new AdminHeadTwo();
            adminHeadTwo.setYear(year);
            adminHeadTwo.setMonthName(monthNames);
            adminHeadTwo.setMonth(month);
            adminHeadTwoRepo.save(adminHeadTwo);
            addHeadOne(num);
        }
    }

    public AdminHeadOne getHeadOneDefault() {

        Optional<AdminHeadOne> byYearAndMonth = adminHeadOneRepo.findByYearAndMonth(year, month);
        return byYearAndMonth.get();

    }
    public AdminHeadOne getHeadOne(Short year, Byte month) {

        Optional<AdminHeadOne> byYearAndMonth = adminHeadOneRepo.findByYearAndMonth(year, month);
        return byYearAndMonth.get();

    }
    public AdminHeadTwo getHeadTwo(Short year, Byte month) {

        Optional<AdminHeadTwo> byYearAndMonth = adminHeadTwoRepo.findByYearAndMonth(year, month);
        return byYearAndMonth.get();

    }

    public AdminHeadTwo getHeadTwoDefault() {

        Optional<AdminHeadTwo> adminHeadTwo = adminHeadTwoRepo.findByYearAndMonth(year, month);
        return adminHeadTwo.get();


    }















//    public void edit(Byte num) {
//        Optional<AdminEntity> adminEntity = adminRepo.findById(1L);
//        AdminEntity adminEntity1 = adminEntity.get();
//
//        switch (num) {
//
//            case 1:
//                Long page1 = adminEntity1.getPage1();
//                page1++;
//                adminEntity1.setPage1(page1);
//                break;
//
//            case 2:
//                Long page2 = adminEntity1.getPage2();
//                page2++;
//                adminEntity1.setPage2(page2);
//                break;
//
//            case 3:
//                Long page3 = adminEntity1.getPage3();
//                page3++;
//                adminEntity1.setPage3(page3);
//                break;
//
//            case 4:
//                Long page4 = adminEntity1.getPage4();
//                page4++;
//                adminEntity1.setPage4(page4);
//                break;
//
//            case 5:
//                Long page5 = adminEntity1.getPage5();
//                page5++;
//                adminEntity1.setPage5(page5);
//                break;
//
//            case 6:
//                Long page6 = adminEntity1.getPage6();
//                page6++;
//                adminEntity1.setPage6(page6);
//                break;
//
//            case 7:
//                Long page7 = adminEntity1.getPage7();
//                page7++;
//                adminEntity1.setPage7(page7);
//                break;
//
//            case 8:
//                Long page8 = adminEntity1.getPage8();
//                page8++;
//                adminEntity1.setPage8(page8);
//                break;
//
//            case 9:
//                Long page9 = adminEntity1.getPage9();
//                page9++;
//                adminEntity1.setPage9(page9);
//                break;
//
//            case 10:
//                Long page10 = adminEntity1.getPage10();
//                page10++;
//                adminEntity1.setPage10(page10);
//                break;
//
//            case 11:
//                Long page11 = adminEntity1.getPage11();
//                page11++;
//                adminEntity1.setPage1(page11);
//                break;
//
//            case 12:
//                Long calls = adminEntity1.getCalls();
//                calls++;
//                adminEntity1.setCalls(calls);
//                break;
//
//            case 13:
//                Long buys = adminEntity1.getBuys();
//                buys++;
//                adminEntity1.setBuys(buys);
//                break;
//
//            case 14:
//                Long visitors = adminEntity1.getVisitors();
//                visitors++;
//                adminEntity1.setVisitors(visitors);
//                break;
//
//            case 15:
//                Long paid = adminEntity1.getPaid();
//                paid++;
//                adminEntity1.setPaid(paid);
//                break;
//
//            case 16:
//                Long exits = adminEntity1.getExits();
//                exits++;
//                adminEntity1.setPaid(exits);
//                break;
//
//            case 17:
//                Long trial = adminEntity1.getTrial();
//                trial++;
//                adminEntity1.setPaid(trial);
//                break;
//
//            default: break;
//
//        }
//    }

//    public Long get(Byte id) {
//
//        Optional<AdminEntity> adminEntity = adminRepo.findById(1L);
//        AdminEntity adminEntity1 = adminEntity.get();
//
//
//        switch (id) {
//
//            case 1:
//                return adminEntity1.getPage1();
//
//            case 2:
//                return adminEntity1.getPage2();
//
//            case 3:
//                return adminEntity1.getPage3();
//
//            case 4:
//                return adminEntity1.getPage4();
//
//            case 5:
//                return adminEntity1.getPage5();
//
//            case 6:
//                return adminEntity1.getPage6();
//
//            case 7:
//                return adminEntity1.getPage7();
//
//            case 8:
//                return adminEntity1.getPage8();
//
//            case 9:
//                return adminEntity1.getPage9();
//
//            case 10:
//                return adminEntity1.getPage10();
//
//            case 11:
//                return adminEntity1.getPage11();
//
//            case 12:
//                return adminEntity1.getCalls();
//
//            case 13:
//                return adminEntity1.getBuys();
//
//            case 14:
//                return adminEntity1.getVisitors();
//
//            case 15:
//                return adminEntity1.getPaid();
//
//            case 16:
//                return adminEntity1.getExits();
//
//            case 17:
//                return adminEntity1.getTrial();
//
//            default: return 0L;
//
//        }
//    }


}
