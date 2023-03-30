package uz.gullbozor.gullbozor.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.dto.GetPriceList;
import uz.gullbozor.gullbozor.dto.NewOrderParam;
import uz.gullbozor.gullbozor.entity.*;
import uz.gullbozor.gullbozor.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class OrderCalculateService {

    @Autowired
    private OtherPriceRepo otherPriceRepo;

    @Autowired
    private ProductHeadRepo productHeadRepo;

    @Autowired
    private GlassRepo glassRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private OrderBodyPvhWinRepo orderBodyPvhWinRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private  NewComRepo newComRepo;

    @Autowired
    private SmsRepo smsRepo;
    @Autowired
    private ProductHeadRepo productHeadRepository;
    @Autowired
    private OrderRepo orderRepo;

    public Integer getPvhL(Integer width, Integer height) {
        return (width + height * 2) + 120;
    }

    public Double getShelfPrice(Byte size, Integer length2, Long companyId, Byte colorNum) {

        double length = ((double) length2 / 1000 );

        Optional<OthersPrice> byCompanyId = otherPriceRepo.findByCompanyId(companyId);
        OthersPrice othersPrice = byCompanyId.get();

        if (colorNum == 1) {

            switch (size) {
                case 15: {
                    Double shelf15Price = othersPrice.getShelf15Price();
                    shelf15Price = (shelf15Price * length);
                    return shelf15Price;
                }
                case 20: {
                    Double shelf20Price = othersPrice.getShelf20Price();
                    shelf20Price = (shelf20Price * length);
                    return shelf20Price;
                }
                case 25: {
                    Double shelf25Price = othersPrice.getShelf25Price();
                    shelf25Price = (shelf25Price * length);
                    return shelf25Price;
                }
                case 30: {
                    Double shelf30Price = othersPrice.getShelf30Price();
                    shelf30Price = (shelf30Price * length);
                    return shelf30Price;
                }
                case 35: {
                    Double shelf35Price = othersPrice.getShelf35Price();
                    shelf35Price = (shelf35Price * length);
                    return shelf35Price;
                }
                case 40: {
                    Double shelf40Price = othersPrice.getShelf40Price();
                    shelf40Price = (shelf40Price * length);
                    return shelf40Price;
                }
                case 45: {
                    Double shelf45Price = othersPrice.getShelf45Price();
                    shelf45Price = (shelf45Price * length);
                    return shelf45Price;
                }
                default: {
                    return 0d;
                }
            }
        } else {
            switch (size) {
                case 15: {
                    Double shelf15Price = othersPrice.getShelf15PriceColor();
                    shelf15Price = (shelf15Price * length);
                    return shelf15Price;
                }
                case 20: {
                    Double shelf20Price = othersPrice.getShelf20PriceColor();
                    shelf20Price = (shelf20Price * length);
                    return shelf20Price;
                }
                case 25: {
                    Double shelf25Price = othersPrice.getShelf25PriceColor();
                    shelf25Price = (shelf25Price * length);
                    return shelf25Price;
                }
                case 30: {
                    Double shelf30Price = othersPrice.getShelf30PriceColor();
                    shelf30Price = (shelf30Price * length);
                    return shelf30Price;
                }
                case 35: {
                    Double shelf35Price = othersPrice.getShelf35PriceColor();
                    shelf35Price = (shelf35Price * length);
                    return shelf35Price;
                }
                case 40: {
                    Double shelf40Price = othersPrice.getShelf40PriceColor();
                    shelf40Price = (shelf40Price * length);
                    return shelf40Price;
                }
                case 45: {
                    Double shelf45Price = othersPrice.getShelf45PriceColor();
                    shelf45Price = (shelf45Price * length);
                    return shelf45Price;
                }
                default: {
                    return 0d;
                }
            }


        }


    }

    public Double getGlassPrice(Double kvadrat, Byte glassNum, Long companyId) {

        Optional<OthersPrice> byCompanyId = otherPriceRepo.findByCompanyId(companyId);
        OthersPrice othersPrice = byCompanyId.get();


        switch (glassNum) {
            case 1: {
                return (kvadrat * othersPrice.getOyna1Price());
            }
            case 2: {
                return (kvadrat * othersPrice.getOyna2Price());
            }
            case 3: {
                return (kvadrat * othersPrice.getOyna3Price());
            }
            case 4: {
                return (kvadrat * othersPrice.getOyna4Price());
            }
            default: {
                return 0d;
            }

        }

    }

    public ApiResponse getOnlyPrice(List<NewOrderParam> newOrderParamList) {


        Double cost = 0d;
        Double lastTotalPrice = 0d;
        Double totalPrice = 0d;
        Double partOfCompany = 0d;
        NewOrderParam newOrderParam1 = newOrderParamList.get(0);
        Order order = new Order();
        Long id = order.getId();



        for (NewOrderParam newOrderParam : newOrderParamList) {

            OrderBodyPvhWin orderBodyPvhWin = calculateOrder(newOrderParam);

            ProductHead productHead = new ProductHead();
            productHead.setOrderId(id);
            productHead.setCategory(orderBodyPvhWin.getCategoryNum());
            productHead.setHeight(orderBodyPvhWin.getHeight());
            productHead.setWidth(orderBodyPvhWin.getWidth());
            productHead.setColorNumber(orderBodyPvhWin.getColor());
            productHead.setGlassNumber(orderBodyPvhWin.getGlassTypeNum());
            productHead.setShelfSize(orderBodyPvhWin.getShelf_size());
            productHead.setTotalPrice(orderBodyPvhWin.getTotalPrice());
            productHead.setCost(orderBodyPvhWin.getOrderCost());
            productHead.setPartOfCompany(orderBodyPvhWin.getCompanyPartPrice());


            cost += productHead.getCost();
            totalPrice += productHead.getTotalPrice();
            partOfCompany += productHead.getPartOfCompany();


        }

        order.setOrderHolderName(newOrderParam1.getOrderOwnerName());
        order.setOrderHolderPhone(newOrderParam1.getOrderOwnerPhone());

        order.setStatus((byte) 0);
        order.setTotalPrice(totalPrice);
        order.setCost(cost);
        order.setLastTotalPrice(lastTotalPrice);
        order.setPartOfCompany(partOfCompany);
        order.setCompanyId(newOrderParam1.getCompanyId());

        Order save = orderRepo.save(order);

        return new ApiResponse(save);

    }


    public List<GetPriceList> getPriceLists(NewOrderParam newOrderParam, Integer cityId) {

        GetPriceList getPriceList = new GetPriceList();
        List<GetPriceList> getPriceLists = new ArrayList<>();
        int a = 0;

        for (CompanyEntity companyEntity : companyRepo.findAllByCityId(cityId)) {


            Long companyId = companyEntity.getId();

            newOrderParam.setCompanyId(companyId);
            newOrderParam.setResponseToCompany(false);

            OrderBodyPvhWin orderBodyPvhWin = calculateOrder(newOrderParam);



            getPriceList.setLocation(companyEntity.getLocation());
            getPriceList.setTgLink(companyEntity.getTgLink());
            getPriceList.setStars(companyEntity.getStars());
            getPriceList.setCompanyName(companyEntity.getCompanyName());

            getPriceLists.add(getPriceList);

            a++;
            if (a==15) return getPriceLists;


        }
        return getPriceLists;
    }

    public OrderBodyPvhWin calculateOrder(NewOrderParam newOrderParam) {

        Integer category = newOrderParam.getCategory();
        Optional<OthersPrice> byyCompanyId = otherPriceRepo.findByCompanyId(newOrderParam.getCompanyId());
        if (byyCompanyId.isEmpty()) {
                return null;
        }
        OthersPrice othersPrice = byyCompanyId.get();

        OrderBodyPvhWin orderBodyPvhWin = new OrderBodyPvhWin();
        orderBodyPvhWin.setPvhTSum(0d);
        orderBodyPvhWin.setPvhZSum(0d);
        orderBodyPvhWin.setPvhTSum(0d);
        orderBodyPvhWin.setShtapikSum(0d);
        orderBodyPvhWin.setShelfSum(0d);


        Double pvh60QvtLPrice = othersPrice.getPvh60QvtLPrice();
        Double pvh60QvtLPriceColor = othersPrice.getPvh60QvtLPriceColor();

        Double pvh60TQvtPrice = othersPrice.getPvh60TQvtPrice();
        Double pvh60TQvtPriceColor = othersPrice.getPvh60TQvtPriceColor();

        Double pvh60ZQvtPrice = othersPrice.getPvh60ZQvtPrice();
        Double pvh60ZQvtPriceColor = othersPrice.getPvh60ZQvtPriceColor();

        Double pvh60TrioShtapikTwoPrice = othersPrice.getPvh60TrioShtapikTwoPrice();
        Double pvh60TrioShtapikTwoPriceColor = othersPrice.getPvh60TrioShtapikTwoPriceColor();

        Double lambriPvhPrice = othersPrice.getLambriPvhPrice();
        Double lambriPvhPriceColor = othersPrice.getLambriPvhPriceColor();

        Double lambriAluPrice = othersPrice.getLambriAluPrice();
        Double lambriAluPriceColor = othersPrice.getLambriAluPriceColor();

        Double aluLPrice = othersPrice.getAluLPrice();
        Double aluLPriceColor = othersPrice.getAluLPriceColor();

        Double aluTPrice = othersPrice.getAluTPrice();
        Double aluTPriceColor = othersPrice.getAluTPriceColor();

        Double aluZPrice = othersPrice.getAluZPrice();
        Double aluZPriceColor = othersPrice.getAluZPriceColor();

        Double aluShtapik1Price = othersPrice.getAluShtapik1Price();
        Double aluShtapik1PriceColor = othersPrice.getAluShtapik1PriceColor();

        Double aluShtapik2Price = othersPrice.getAluShtapik2Price();



        ProductHead productHead = new ProductHead();

        Optional<CompanyEntity> optionalCompany = companyRepo.findById(newOrderParam.getCompanyId());
        CompanyEntity companyEntity = optionalCompany.get();

        double totalPrice = 0d;

        int height =  newOrderParam.getHeight();
        int width =  newOrderParam.getWidth();


        orderBodyPvhWin.setColor(newOrderParam.getColorNumber());

        Integer L = null;
        Integer T = null;
        Integer Z = null;
        Integer shtapik = null;
        Integer shtapik2 = null;
        Integer chitLength = null;
        Integer rezinaPvhLength = null;
        Integer part = null;
        Integer widthMini = null;

        Glass glass1 = new Glass();
        Glass glass2 = new Glass();
        Glass glass3 = new Glass();
        Glass glass4 = new Glass();
        Glass glass5 = new Glass();


        double kvadrat = 0L;
        Double partOfWindow = null;

        int middle = 0;

        switch (category) {

            case 1:
            case 2:
            case 3:
            case 4:


//                |------|------|
//                |      |      |
//                |      |      |
//                |      |      |
//                |      |      |
//                |______|______|





                if (category > 2) {
                    orderBodyPvhWin.setIkkitalikMexanizm(othersPrice.getIkkitalikMexanizmPrice());
                }else {
                    orderBodyPvhWin.setIspandiletCount((byte) 1);
                    orderBodyPvhWin.setIspandiletSum(othersPrice.getIspandiletPrice());

                    if (height > 1590) {
                        orderBodyPvhWin.setPetlya((byte) 3);
                        orderBodyPvhWin.setPetlyaSum(othersPrice.getPetlyaPrice() * 3);
                        orderBodyPvhWin.setSamarez((short) 51);
                        orderBodyPvhWin.setSamarezSum((othersPrice.getSamarezPrice() / 1000) * 24);// Togirlash kere***********************

                        totalPrice = (totalPrice + orderBodyPvhWin.getPetlyaSum() + orderBodyPvhWin.getSamarezSum());
                    } else {
                        orderBodyPvhWin.setPetlya((byte) 2);
                        orderBodyPvhWin.setPetlyaSum(othersPrice.getPetlyaPrice() * 3);
                        orderBodyPvhWin.setSamarez((short) 24);
                        orderBodyPvhWin.setSamarezSum((othersPrice.getSamarezPrice() / 1000) * 24);

                        totalPrice = (totalPrice + orderBodyPvhWin.getPetlyaSum() + orderBodyPvhWin.getSamarezSum());
                    }

                }
                // Profillar
                L = getPvhL(width, height);

                T = (height - 96);

                Z = (2 * (T + 21));

                Z = (Z + ((width - 96) / 2) + 2);

                shtapik = (Z - 224);

                //Shtapiklar
                shtapik = (shtapik + (T * 2) + (((width - 96) / 2) - 19) * 2);

                //Oynalar


                glass1.setHeight((double) ((((height - 111) / 10) + ((height - 111) % 10))) / 100d );
                glass1.setWidth((double) ((((((width - 96) / 2) - 34) / 10) + ((((width - 96) / 2) - 34) % 10 ))) / 100d );
                glass1.setCount((byte) 2);
                Glass save = glassRepo.save(glass1);

                orderBodyPvhWin.setGlass1(save.getId());


                glass2.setHeight((double) (((T - 81) / 10)) / 100d );
                glass2.setWidth((double) ((( ( (width - 96) / 2) - 125)/10)) / 100d);
                glass2.setCount((byte) 2);
                Glass save1 = glassRepo.save(glass2);

                orderBodyPvhWin.setGlass2(save1.getId());

                orderBodyPvhWin.setGlassTwoFloor(true);


                //Chit
                chitLength = (int) (2 * (glass1.getHeight() + glass1.getWidth()) + 2 * (glass2.getWidth() + glass2.getHeight()) - 80);
                rezinaPvhLength = (chitLength + 200);

                orderBodyPvhWin.setRezinaPvh(rezinaPvhLength);
                orderBodyPvhWin.setRezinaPvhSum((rezinaPvhLength/1000d) * othersPrice.getRezinaPvhPrice());

                orderBodyPvhWin.setTikol( (chitLength/ 1000) * 20 );
                orderBodyPvhWin.setTikolSum( (orderBodyPvhWin.getTikol() * othersPrice.getTikolPrice()) / 1000);

                orderBodyPvhWin.setPoroshok((chitLength / 1000 ) * 20 );
                orderBodyPvhWin.setPoroshokSum((orderBodyPvhWin.getPoroshok()  * othersPrice.getPoroshokPrice() ) / 1000 );


                if (newOrderParam.getColorNumber() == 1) {

                    orderBodyPvhWin.setPvhLSum((L / 1000) * othersPrice.getPvh60QvtLPrice());
                    orderBodyPvhWin.setPvhTSum((T / 1000) * othersPrice.getPvh60TQvtPrice());
                    orderBodyPvhWin.setPvhZSum((Z / 1000) * othersPrice.getPvh60ZQvtPrice());

                    orderBodyPvhWin.setRuchkaSum(othersPrice.getRuchkaPvhPrice() * 1);

                    orderBodyPvhWin.setShtapikSum((shtapik / 1000) * othersPrice.getPvh60TrioShtapikTwoPrice());
                    totalPrice = (orderBodyPvhWin.getPvhLSum() + orderBodyPvhWin.getPvhTSum() + orderBodyPvhWin.getPvhZSum() + orderBodyPvhWin.getShelfSum());

                } else {

                    orderBodyPvhWin.setPvhLSum((L / 1000) * othersPrice.getPvh60QvtLPriceColor());
                    orderBodyPvhWin.setPvhTSum((T / 1000) * othersPrice.getPvh60TQvtPriceColor());
                    orderBodyPvhWin.setPvhZSum((Z / 1000) * othersPrice.getPvh60ZQvtPriceColor());

                    orderBodyPvhWin.setRuchkaSum(othersPrice.getRuchkaPvhColorPrice() * 1);

                    orderBodyPvhWin.setShtapikSum((shtapik / 1000) * othersPrice.getPvh60TrioShtapikTwoPriceColor());

                    totalPrice += (orderBodyPvhWin.getPvhLSum() + orderBodyPvhWin.getPvhTSum() + orderBodyPvhWin.getPvhZSum() + orderBodyPvhWin.getShelfSum());

                }


                //Narxlarni qo'shish *************************************************************************************

                orderBodyPvhWin.setPvhL(L);
                orderBodyPvhWin.setPvhT(T);
                orderBodyPvhWin.setPvhZ(Z);

                orderBodyPvhWin.setShtapik(shtapik);

                orderBodyPvhWin.setChit(chitLength);
                orderBodyPvhWin.setChitSum((orderBodyPvhWin.getChit() / 1000) * othersPrice.getChitPrice());

                orderBodyPvhWin.setShelfSum(getShelfPrice(newOrderParam.getShelfSize(), (width + 100), newOrderParam.getCompanyId(), newOrderParam.getColorNumber()));
                totalPrice = (totalPrice + orderBodyPvhWin.getShelfSum());
                orderBodyPvhWin.setShelf_size(newOrderParam.getShelfSize());
                orderBodyPvhWin.setShelf_length(width + 100);

                kvadrat = (glass1.getWidth() * glass1.getHeight() + (glass2.getWidth() * glass2.getHeight()));
                kvadrat = (kvadrat + kvadrat );
                orderBodyPvhWin.setGlassKvadrat(  kvadrat );
                orderBodyPvhWin.setGlassSum(getGlassPrice(kvadrat, newOrderParam.getGlassNumber(), newOrderParam.getCompanyId()));

                orderBodyPvhWin.setQoraBurchak((byte) 8);
                orderBodyPvhWin.setSariqBurchak((byte) 2);

                orderBodyPvhWin.setSariqBurchakSum(2 * othersPrice.getSariqBurchakPrice());
                orderBodyPvhWin.setQoraBurchakSum(8 * othersPrice.getQoraBurchakPrice());

                orderBodyPvhWin.setSaydinitel((byte) 2);
                orderBodyPvhWin.setSaydinitelSum(2 * othersPrice.getSaydinitelPrice());

                totalPrice = (totalPrice + orderBodyPvhWin.getGlassSum() + orderBodyPvhWin.getSariqBurchakSum() + orderBodyPvhWin.getQoraBurchakSum());


                orderBodyPvhWin.setWidth(newOrderParam.getWidth());
                orderBodyPvhWin.setWidthMini(widthMini);
                orderBodyPvhWin.setHeight(newOrderParam.getHeight());

                orderBodyPvhWin.setRuchkaTypeNum(newOrderParam.getRuchkaTypeNum());
                orderBodyPvhWin.setGlassTypeNum(newOrderParam.getGlassNumber());


                orderBodyPvhWin.setTotalPrice(totalPrice);
                partOfWindow = companyEntity.getPartOfWindow();
                partOfWindow = ((totalPrice / 100) * partOfWindow);


                orderBodyPvhWin.setOrderCost((int) totalPrice-partOfWindow);

                orderBodyPvhWin.setCompanyPartPrice((double) Math.round(partOfWindow));

                orderBodyPvhWin.setRuchkaTypeNum(newOrderParam.getRuchkaTypeNum());
                orderBodyPvhWin.setCategoryNum(newOrderParam.getCategory());

                return orderBodyPvhWinRepo.save(orderBodyPvhWin);


            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:

//                |----------|---------|
//                |          |         |
//                |__________|_________|
//                |      |      |      |
//                |      |      |      |
//                |      |      |      |
//                |      |      |      |
//                |______|______|______|



                if ((category == 8) || (category == 9)) {
                    orderBodyPvhWin.setIkkitalikMexanizm(othersPrice.getIkkitalikMexanizmPrice());
                } else if (category == 10) {
                    orderBodyPvhWin.setIkkitalikMexanizm(othersPrice.getIkkitalikMexanizmPrice() * 2);
                }
                // Profillar
                L = getPvhL(width, height);

                T = (height - 96);

                Z = (2 * (T + 21));

                Z = (Z + ((width - 96) / 2) + 2);

                shtapik = (Z - 224);

                //Shtapiklar
                shtapik = (shtapik + (T * 2) + (((width - 96) / 2) - 19) * 2);

                //Oynalar


                glass1.setHeight((double) (height - 111));
                glass1.setWidth((double) (((width - 96) / 2) - 34));
                glass1.setCount((byte) 2);


                glass2.setHeight((double) (T - 81));
                glass2.setWidth((double) (((width - 96) / 2) - 125));
                glass2.setCount((byte) 2);

                Glass glassSave1 = glassRepo.save(glass1);
                Glass glassSave2 = glassRepo.save(glass2);

                orderBodyPvhWin.setGlass1(glassSave1.getId());
                orderBodyPvhWin.setGlass2(glassSave2.getId());
                //Chit
                chitLength = (int) (2 * (glass1.getHeight() + glass1.getWidth()) + 2 * (glass2.getWidth() + glass2.getHeight()) - 80);
                rezinaPvhLength = (chitLength + 200);


                if (newOrderParam.getColorNumber() == 1) {

                    orderBodyPvhWin.setPvhLSum((L / 1000) * othersPrice.getPvh60QvtLPrice());
                    orderBodyPvhWin.setPvhTSum((T / 1000) * othersPrice.getPvh60TQvtPrice());
                    orderBodyPvhWin.setPvhZSum((Z / 1000) * othersPrice.getPvh60ZQvtPrice());

                    orderBodyPvhWin.setShtapikSum((shtapik / 1000) * othersPrice.getPvh60TrioShtapikTwoPrice());

                    totalPrice = (orderBodyPvhWin.getPvhLSum() + orderBodyPvhWin.getPvhTSum() + orderBodyPvhWin.getPvhZSum() + orderBodyPvhWin.getShelfSum());

                } else {

                    orderBodyPvhWin.setPvhLSum((L / 1000) * othersPrice.getPvh60QvtLPriceColor());
                    orderBodyPvhWin.setPvhTSum((T / 1000) * othersPrice.getPvh60TQvtPriceColor());
                    orderBodyPvhWin.setPvhZSum((Z / 1000) * othersPrice.getPvh60ZQvtPriceColor());

                    orderBodyPvhWin.setShtapikSum((shtapik / 1000) * othersPrice.getPvh60TrioShtapikTwoPriceColor());

                    totalPrice += (orderBodyPvhWin.getPvhLSum() + orderBodyPvhWin.getPvhTSum() + orderBodyPvhWin.getPvhZSum() + orderBodyPvhWin.getShelfSum());

                }


                //Narxlarni qo'shish *************************************************************************************

                orderBodyPvhWin.setPvhL(L);
                orderBodyPvhWin.setPvhT(T);
                orderBodyPvhWin.setPvhZ(Z);
                orderBodyPvhWin.setHeight(height);
                orderBodyPvhWin.setWidth(width);
                orderBodyPvhWin.setWidthMini(widthMini);
                orderBodyPvhWin.setGlassTypeNum(newOrderParam.getGlassNumber());


                orderBodyPvhWin.setShtapik(shtapik);
                orderBodyPvhWin.setShtapikSum((shtapik / 1000) * othersPrice.getAluShtapik2Price());

                orderBodyPvhWin.setChit(chitLength);
                orderBodyPvhWin.setChitSum((orderBodyPvhWin.getChit() / 1000) * othersPrice.getChitPrice());

                orderBodyPvhWin.setShelfSum(getShelfPrice(newOrderParam.getShelfSize(), (width + 100), newOrderParam.getCompanyId(), newOrderParam.getColorNumber()));
                totalPrice = (totalPrice + orderBodyPvhWin.getShelfSum());
                orderBodyPvhWin.setShelf_size(newOrderParam.getShelfSize());
                orderBodyPvhWin.setShelf_length(width + 100);

                kvadrat = (glass1.getWidth() * glass1.getHeight() + (glass2.getWidth() * glass2.getHeight()));
                orderBodyPvhWin.setGlassKvadrat(kvadrat);
                orderBodyPvhWin.setGlassSum(getGlassPrice(kvadrat, newOrderParam.getGlassNumber(), newOrderParam.getCompanyId()));

                orderBodyPvhWin.setQoraBurchak((byte) 8);
                orderBodyPvhWin.setSariqBurchak((byte) 2);

                orderBodyPvhWin.setSariqBurchakSum(2 * othersPrice.getSariqBurchakPrice());
                orderBodyPvhWin.setQoraBurchakSum(8 * othersPrice.getQoraBurchakPrice());

                orderBodyPvhWin.setSaydinitel((byte) 2);
                orderBodyPvhWin.setSaydinitelSum(2 * othersPrice.getSaydinitelPrice());

                totalPrice = (totalPrice + orderBodyPvhWin.getGlassSum() + orderBodyPvhWin.getSariqBurchakSum() + orderBodyPvhWin.getQoraBurchakSum());
                if (height > 1490) {
                    orderBodyPvhWin.setPetlya((byte) 3);
                    orderBodyPvhWin.setPetlyaSum(othersPrice.getPetlyaPrice() * 3);
                    orderBodyPvhWin.setSamarez((short) 51);
                    orderBodyPvhWin.setSamarezSum((othersPrice.getSamarezPrice() / 1000) * 24);// Togirlash kere***********************

                    totalPrice = (totalPrice + orderBodyPvhWin.getPetlyaSum() + orderBodyPvhWin.getSamarezSum());
                } else {
                    orderBodyPvhWin.setPetlya((byte) 2);
                    orderBodyPvhWin.setPetlyaSum(othersPrice.getPetlyaPrice() * 3);
                    orderBodyPvhWin.setSamarez((short) 24);
                    orderBodyPvhWin.setSamarezSum((othersPrice.getSamarezPrice() / 1000) * 24);

                    totalPrice = (totalPrice + orderBodyPvhWin.getPetlyaSum() + orderBodyPvhWin.getSamarezSum());
                }



                orderBodyPvhWin.setRuchkaTypeNum(newOrderParam.getRuchkaTypeNum());
                orderBodyPvhWin.setCategoryNum(newOrderParam.getCategory());

                orderBodyPvhWin.setTotalPrice(totalPrice);

                partOfWindow = companyEntity.getPartOfWindow();

                partOfWindow = ((totalPrice / 100) * partOfWindow);


                orderBodyPvhWin.setCompanyPartPrice(partOfWindow);





            case 11:
            case 12:
            case 13:
            case 14:


//                |------|------|------|
//                |      |      |      |
//                |      |      |      |
//                |      |      |      |
//                |      |      |      |
//                |______|______|______|


                if ((category == 13) || category == 14) {
                    orderBodyPvhWin.setIkkitalikMexanizm(othersPrice.getIkkitalikMexanizmPrice());
                } else {
                    orderBodyPvhWin.setIkkitalikMexanizm(0d);
                }

                // Profillar
                L = getPvhL(width, height);

                T = ((height - 96) * 2);

                part = ((width - 270) / 3);

                widthMini = (part + 67);

                orderBodyPvhWin.setWidthMini(widthMini);

                middle = ((width - 172) - (part * 2));

                Z = (((height + 21) * 2) + ((middle + 21) * 2));

                shtapik = ((Z - 224) + (widthMini * 4) + (height - 96) * 4);


                glass1.setHeight((double) (height - 111));
                glass1.setWidth((double) (part - 15));
                glass1.setCount((byte) 4);

                glass2.setHeight((double) (T - 82));
                glass2.setWidth((double) (middle - 82));
                glass2.setCount((byte) 2);

                glassRepo.save(glass1);
                glassRepo.save(glass2);


                //Chit
                chitLength = (int) (2 * (2 * (glass1.getHeight() + glass1.getWidth())) + (glass2.getWidth() + glass2.getHeight()) * 2 - 120);
                rezinaPvhLength = (chitLength + 200);


                if (newOrderParam.getColorNumber() == 1) {

                    orderBodyPvhWin.setPvhLSum((L / 1000) * othersPrice.getPvh60QvtLPrice());
                    orderBodyPvhWin.setPvhTSum((T / 1000) * othersPrice.getPvh60TQvtPrice());
                    orderBodyPvhWin.setPvhZSum((Z / 1000) * othersPrice.getPvh60ZQvtPrice());

                    orderBodyPvhWin.setShtapikSum((shtapik / 1000) * othersPrice.getPvh60TrioShtapikTwoPrice());
                    totalPrice = (orderBodyPvhWin.getPvhLSum() + orderBodyPvhWin.getPvhTSum() + orderBodyPvhWin.getPvhZSum() + orderBodyPvhWin.getShelfSum());

                } else {

                    orderBodyPvhWin.setPvhLSum((L / 1000) * othersPrice.getPvh60QvtLPriceColor());
                    orderBodyPvhWin.setPvhTSum((T / 1000) * othersPrice.getPvh60TQvtPriceColor());
                    orderBodyPvhWin.setPvhZSum((Z / 1000) * othersPrice.getPvh60ZQvtPriceColor());

                    orderBodyPvhWin.setShtapikSum((shtapik / 1000) * othersPrice.getPvh60TrioShtapikTwoPriceColor());

                    totalPrice += (orderBodyPvhWin.getPvhLSum() + orderBodyPvhWin.getPvhTSum() + orderBodyPvhWin.getPvhZSum() + orderBodyPvhWin.getShelfSum());

                }


                //Narxlarni qo'shish *************************************************************************************

                orderBodyPvhWin.setPvhL(L);
                orderBodyPvhWin.setPvhT(T);
                orderBodyPvhWin.setPvhZ(Z);


                orderBodyPvhWin.setShtapik(shtapik);

                orderBodyPvhWin.setShelfSum(getShelfPrice(newOrderParam.getShelfSize(), (width + 100), newOrderParam.getCompanyId(), newOrderParam.getColorNumber()));
                totalPrice = (totalPrice + orderBodyPvhWin.getShelfSum());
                orderBodyPvhWin.setShelf_size(newOrderParam.getShelfSize());
                orderBodyPvhWin.setShelf_length(width + 100);

                kvadrat = (glass1.getWidth() * glass1.getHeight() + (glass2.getWidth() * glass2.getHeight()));
                orderBodyPvhWin.setGlassKvadrat(kvadrat);
                orderBodyPvhWin.setGlassSum(getGlassPrice(kvadrat, newOrderParam.getGlassNumber(), newOrderParam.getCompanyId()));


                orderBodyPvhWin.setQoraBurchak((byte) 12);
                orderBodyPvhWin.setSariqBurchak((byte) 2);

                orderBodyPvhWin.setSariqBurchakSum(orderBodyPvhWin.getQoraBurchak() * othersPrice.getSariqBurchakPrice());
                orderBodyPvhWin.setQoraBurchakSum(orderBodyPvhWin.getSariqBurchak() * othersPrice.getQoraBurchakPrice());

                orderBodyPvhWin.setSaydinitel((byte) 4);
                orderBodyPvhWin.setSaydinitelSum(orderBodyPvhWin.getSaydinitel() * othersPrice.getSaydinitelPrice());

                totalPrice = (totalPrice + orderBodyPvhWin.getGlassSum() + orderBodyPvhWin.getSariqBurchakSum() + orderBodyPvhWin.getQoraBurchakSum());
                if (height > 1490) {
                    orderBodyPvhWin.setPetlya((byte) 3);
                    orderBodyPvhWin.setPetlyaSum(othersPrice.getPetlyaPrice() * orderBodyPvhWin.getPetlya());
                    orderBodyPvhWin.setSamarez((short) 51);
                    orderBodyPvhWin.setSamarezSum((othersPrice.getSamarezPrice() / 1000) * 24);// Togirlash kere***********************

                    totalPrice = (totalPrice + orderBodyPvhWin.getPetlyaSum() + orderBodyPvhWin.getSamarezSum());
                } else {
                    orderBodyPvhWin.setPetlya((byte) 2);
                    orderBodyPvhWin.setPetlyaSum(othersPrice.getPetlyaPrice() * 3);
                    orderBodyPvhWin.setSamarez((short) 24);
                    orderBodyPvhWin.setSamarezSum((othersPrice.getSamarezPrice() / 1000) * 24);

                    totalPrice = (totalPrice + orderBodyPvhWin.getPetlyaSum() + orderBodyPvhWin.getSamarezSum());
                }

                orderBodyPvhWin.setWidth(width);
                orderBodyPvhWin.setWidthMini(widthMini);

                orderBodyPvhWin.setRuchkaTypeNum(newOrderParam.getRuchkaTypeNum());
                orderBodyPvhWin.setCategoryNum(newOrderParam.getCategory());


                orderBodyPvhWin.setTotalPrice(totalPrice);

                partOfWindow = companyEntity.getPartOfWindow();


                partOfWindow = ((totalPrice / 100) * partOfWindow);


                orderBodyPvhWin.setCompanyPartPrice(partOfWindow);



                if (newOrderParam.isResponseToCompany()){
                    return orderBodyPvhWin;
                }else {
                    return orderBodyPvhWin;
                }


            case 15:
            case 16:
            case 17:
            case 18:

//                |------|------|------|
//                |      |      |      |
//                |      |      |      |
//                |      |      |      |
//                |      |      |      |
//                |______|______|______|


                if ((category == 16) || category == 17) {
                    orderBodyPvhWin.setIkkitalikMexanizm(othersPrice.getIkkitalikMexanizmPrice());
                } else if (category == 18) {
                    orderBodyPvhWin.setIkkitalikMexanizm(othersPrice.getIkkitalikMexanizmPrice() * 2);
                }

                // Profillar
                L = getPvhL(width, height);

                T = ((height - 96) * 2);

                part = ((width - 270) / 3);

                widthMini = (part + 67);

                orderBodyPvhWin.setWidthMini(widthMini);

                Z = 4 * (part + 42 + T);

                shtapik = (Z - 448);

                shtapik = (shtapik + ((T * 2) + ((part) * 2)));

                glass1.setHeight((double) (height - 111));
                glass1.setWidth((double) (part - 15));
                glass1.setCount((byte) 2);

                glass2.setHeight((double) (T - 112));
                glass2.setWidth((double) (part - 112));
                glass2.setCount((byte) 4);

                glassRepo.save(glass1);
                glassRepo.save(glass2);

                //Chit
                chitLength = (int) (2 * (glass1.getHeight() + glass1.getWidth()) - 120);
                rezinaPvhLength = (shtapik);


                if (newOrderParam.getColorNumber() == 1) {

                    orderBodyPvhWin.setPvhLSum((L / 1000) * othersPrice.getPvh60QvtLPrice());
                    orderBodyPvhWin.setPvhTSum((T / 1000) * othersPrice.getPvh60TQvtPrice());
                    orderBodyPvhWin.setPvhZSum((Z / 1000) * othersPrice.getPvh60ZQvtPrice());

                    orderBodyPvhWin.setShtapikSum((shtapik / 1000) * othersPrice.getPvh60TrioShtapikTwoPrice());
                    totalPrice = (orderBodyPvhWin.getPvhLSum() + orderBodyPvhWin.getPvhTSum() + orderBodyPvhWin.getPvhZSum() + orderBodyPvhWin.getShelfSum());

                } else {

                    orderBodyPvhWin.setPvhLSum((L / 1000) * othersPrice.getPvh60QvtLPriceColor());
                    orderBodyPvhWin.setPvhTSum((T / 1000) * othersPrice.getPvh60TQvtPriceColor());
                    orderBodyPvhWin.setPvhZSum((Z / 1000) * othersPrice.getPvh60ZQvtPriceColor());

                    orderBodyPvhWin.setShtapikSum((shtapik / 1000) * othersPrice.getPvh60TrioShtapikTwoPriceColor());

                    totalPrice += (orderBodyPvhWin.getPvhLSum() + orderBodyPvhWin.getPvhTSum() + orderBodyPvhWin.getPvhZSum() + orderBodyPvhWin.getShelfSum());

                }


                //Narxlarni qo'shish *************************************************************************************

                orderBodyPvhWin.setPvhL(L);
                orderBodyPvhWin.setPvhT(T);
                orderBodyPvhWin.setPvhZ(Z);


                orderBodyPvhWin.setShtapik(shtapik);

                orderBodyPvhWin.setShelfSum(getShelfPrice(newOrderParam.getShelfSize(), (width + 100), newOrderParam.getCompanyId(), newOrderParam.getColorNumber()));
                totalPrice = (totalPrice + orderBodyPvhWin.getShelfSum());
                orderBodyPvhWin.setShelf_size(newOrderParam.getShelfSize());
                orderBodyPvhWin.setShelf_length(width + 100);

                kvadrat = (glass1.getWidth() * glass1.getHeight() + (glass2.getWidth() * glass2.getHeight()));
                orderBodyPvhWin.setGlassKvadrat(kvadrat);
                orderBodyPvhWin.setGlassSum(getGlassPrice(kvadrat, newOrderParam.getGlassNumber(), newOrderParam.getCompanyId()));

                orderBodyPvhWin.setQoraBurchak((byte) 12);
                orderBodyPvhWin.setSariqBurchak((byte) 2);

                orderBodyPvhWin.setSariqBurchakSum(orderBodyPvhWin.getQoraBurchak() * othersPrice.getSariqBurchakPrice());
                orderBodyPvhWin.setQoraBurchakSum(orderBodyPvhWin.getSariqBurchak() * othersPrice.getQoraBurchakPrice());

                orderBodyPvhWin.setSaydinitel((byte) 4);
                orderBodyPvhWin.setSaydinitelSum(orderBodyPvhWin.getSaydinitel() * othersPrice.getSaydinitelPrice());

                totalPrice = (totalPrice + orderBodyPvhWin.getGlassSum() + orderBodyPvhWin.getSariqBurchakSum() + orderBodyPvhWin.getQoraBurchakSum());
                if (height > 1490) {
                    orderBodyPvhWin.setPetlya((byte) 3);
                    orderBodyPvhWin.setPetlyaSum(othersPrice.getPetlyaPrice() * orderBodyPvhWin.getPetlya());
                    orderBodyPvhWin.setSamarez((short) 51);
                    orderBodyPvhWin.setSamarezSum((othersPrice.getSamarezPrice() / 1000) * 24);// Togirlash kere***********************

                    totalPrice = (totalPrice + orderBodyPvhWin.getPetlyaSum() + orderBodyPvhWin.getSamarezSum());
                } else {
                    orderBodyPvhWin.setPetlya((byte) 2);
                    orderBodyPvhWin.setPetlyaSum(othersPrice.getPetlyaPrice() * 3);
                    orderBodyPvhWin.setSamarez((short) 24);
                    orderBodyPvhWin.setSamarezSum((othersPrice.getSamarezPrice() / 1000) * 24);

                    totalPrice = (totalPrice + orderBodyPvhWin.getPetlyaSum() + orderBodyPvhWin.getSamarezSum());
                }

                orderBodyPvhWin.setWidth(width);
                orderBodyPvhWin.setWidthMini(widthMini);


                orderBodyPvhWin.setRuchkaTypeNum(newOrderParam.getRuchkaTypeNum());
                orderBodyPvhWin.setCategoryNum(newOrderParam.getCategory());

                orderBodyPvhWin.setTotalPrice(totalPrice);

                partOfWindow = companyEntity.getPartOfWindow();
                partOfWindow = ((totalPrice / 100) * partOfWindow);


                orderBodyPvhWin.setCompanyPartPrice(partOfWindow);



                if (newOrderParam.isResponseToCompany()){
                    return orderBodyPvhWin;
                }else {
                    return orderBodyPvhWin;
                }

            case 19:
            case 20:

//  Eshik
//               |-------------|
//               |             |
//               |             |
//               |             |
//               |             |
//               |             |
//               |             |
//               |-------------|
//               |  |  |  |  | |
//               |  |  |  |  | |
//               |  |  |  |  | |
//               |__|__|__|__|_|




                // Profillar
                L = getPvhL(width, height);

                T = ((height - 96) * 2);

                part = ((width - 270) / 3);

                widthMini = (part + 67);

                orderBodyPvhWin.setWidthMini(widthMini);

                Z = 4 * (part + 42 + T);

                shtapik = (Z - 448);

                shtapik = (shtapik + ((T * 2) + ((part) * 2)));

                glass1.setHeight((double) (height - 111));
                glass1.setWidth((double) (part - 15));
                glass1.setCount((byte) 2);

                glass2.setHeight((double) (T - 112));
                glass2.setWidth((double) (part - 112));
                glass2.setCount((byte) 4);

                glassRepo.save(glass1);
                glassRepo.save(glass2);

                //Chit
                chitLength = (int) (2 * (glass1.getHeight() + glass1.getWidth()) - 120);
                rezinaPvhLength = (shtapik);


                if (newOrderParam.getColorNumber() == 1) {

                    orderBodyPvhWin.setPvhLSum((L / 1000) * othersPrice.getPvh60QvtLPrice());
                    orderBodyPvhWin.setPvhTSum((T / 1000) * othersPrice.getPvh60TQvtPrice());
                    orderBodyPvhWin.setPvhZSum((Z / 1000) * othersPrice.getPvh60ZQvtPrice());

                    orderBodyPvhWin.setShtapikSum((shtapik / 1000) * othersPrice.getPvh60TrioShtapikTwoPrice());
                    totalPrice = (orderBodyPvhWin.getPvhLSum() + orderBodyPvhWin.getPvhTSum() + orderBodyPvhWin.getPvhZSum() + orderBodyPvhWin.getShelfSum());

                } else {

                    orderBodyPvhWin.setPvhLSum((L / 1000) * othersPrice.getPvh60QvtLPriceColor());
                    orderBodyPvhWin.setPvhTSum((T / 1000) * othersPrice.getPvh60TQvtPriceColor());
                    orderBodyPvhWin.setPvhZSum((Z / 1000) * othersPrice.getPvh60ZQvtPriceColor());

                    orderBodyPvhWin.setShtapikSum((shtapik / 1000) * othersPrice.getPvh60TrioShtapikTwoPriceColor());

                    totalPrice += (orderBodyPvhWin.getPvhLSum() + orderBodyPvhWin.getPvhTSum() + orderBodyPvhWin.getPvhZSum() + orderBodyPvhWin.getShelfSum());

                }


                //Narxlarni qo'shish *************************************************************************************

                orderBodyPvhWin.setPvhL(L);
                orderBodyPvhWin.setPvhT(T);
                orderBodyPvhWin.setPvhZ(Z);


                orderBodyPvhWin.setShtapik(shtapik);

                orderBodyPvhWin.setShelfSum(getShelfPrice(newOrderParam.getShelfSize(), (width + 100), newOrderParam.getCompanyId(), newOrderParam.getColorNumber()));
                totalPrice = (totalPrice + orderBodyPvhWin.getShelfSum());
                orderBodyPvhWin.setShelf_size(newOrderParam.getShelfSize());
                orderBodyPvhWin.setShelf_length(width + 100);

                kvadrat = (glass1.getWidth() * glass1.getHeight() + (glass2.getWidth() * glass2.getHeight()));
                orderBodyPvhWin.setGlassKvadrat(kvadrat);
                orderBodyPvhWin.setGlassSum(getGlassPrice(kvadrat, newOrderParam.getGlassNumber(), newOrderParam.getCompanyId()));

                orderBodyPvhWin.setQoraBurchak((byte) 12);
                orderBodyPvhWin.setSariqBurchak((byte) 2);

                orderBodyPvhWin.setSariqBurchakSum(orderBodyPvhWin.getQoraBurchak() * othersPrice.getSariqBurchakPrice());
                orderBodyPvhWin.setQoraBurchakSum(orderBodyPvhWin.getSariqBurchak() * othersPrice.getQoraBurchakPrice());

                orderBodyPvhWin.setSaydinitel((byte) 4);
                orderBodyPvhWin.setSaydinitelSum(orderBodyPvhWin.getSaydinitel() * othersPrice.getSaydinitelPrice());

                totalPrice = (totalPrice + orderBodyPvhWin.getGlassSum() + orderBodyPvhWin.getSariqBurchakSum() + orderBodyPvhWin.getQoraBurchakSum());
                if (height > 1490) {
                    orderBodyPvhWin.setPetlya((byte) 3);
                    orderBodyPvhWin.setPetlyaSum(othersPrice.getPetlyaPrice() * orderBodyPvhWin.getPetlya());
                    orderBodyPvhWin.setSamarez((short) 51);
                    orderBodyPvhWin.setSamarezSum((othersPrice.getSamarezPrice() / 1000) * 24);// Togirlash kere***********************

                    totalPrice = (totalPrice + orderBodyPvhWin.getPetlyaSum() + orderBodyPvhWin.getSamarezSum());
                } else {
                    orderBodyPvhWin.setPetlya((byte) 2);
                    orderBodyPvhWin.setPetlyaSum(othersPrice.getPetlyaPrice() * 3);
                    orderBodyPvhWin.setSamarez((short) 24);
                    orderBodyPvhWin.setSamarezSum((othersPrice.getSamarezPrice() / 1000) * 24);

                    totalPrice = (totalPrice + orderBodyPvhWin.getPetlyaSum() + orderBodyPvhWin.getSamarezSum());
                }


                orderBodyPvhWin.setTotalPrice(totalPrice);

                partOfWindow = companyEntity.getPartOfWindow();


                partOfWindow = ((totalPrice / 100) * partOfWindow);


                orderBodyPvhWin.setCompanyPartPrice(partOfWindow);


                if (newOrderParam.isResponseToCompany()){
                    return orderBodyPvhWin;
                }else {
                    return orderBodyPvhWin;
                }


            case 21:
                break;
            case 22:
                break;
            case 23:
                break;
            case 24:
                break;
            case 25:
                break;
            case 26:
                break;
            case 27:
                break;
            case 28:
                break;
            case 29:
                break;
            case 30:
                break;
            case 31:
                break;
            case 32:
                break;

            default:
                throw new IllegalStateException("Mavjud bo'lmagan kategorya raqami: " + category);
        }
        return null;
    }


    public Long cat1ColorBy(@NotNull NewOrderParam newOrderParam, NewCom newCom) {

        Byte colorNumber = newOrderParam.getColorNumber();

        Long sum = null;

        switch (colorNumber) {

            case 1:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory1Oq());
                break;
            case 2:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory1Zal());
                break;
            case 3:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory1Mokko());
                break;
            case 4:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory1Mokry());
                break;
            default: break;
        }
        return sum;


    }

    public Long cat2ColorBy(NewOrderParam newOrderParam, NewCom newCom) {

        Byte colorNumber = newOrderParam.getColorNumber();

        Long sum = null;

        switch (colorNumber) {

            case 1:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory2Oq());
                break;
            case 2:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory2Zal());
                break;
            case 3:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory2Mokko());
                break;
            case 4:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory2Mokry());
                break;
            default: break;
        }
        return sum;


    }

    public Long cat3ColorBy(NewOrderParam newOrderParam, NewCom newCom) {

        Byte colorNumber = newOrderParam.getColorNumber();

        Long sum = null;

        switch (colorNumber) {

            case 1:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory3Oq());
                break;
            case 2:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory3Zal());
                break;
            case 3:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory3Mokko());
                break;
            case 4:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory3Mokry());
                break;
            default: break;
        }
        return sum;


    }

    public Long cat4ColorBy(NewOrderParam newOrderParam, NewCom newCom) {

        Byte colorNumber = newOrderParam.getColorNumber();

        Long sum = null;

        switch (colorNumber) {

            case 1:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory4Oq());
                break;
            case 2:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory4Zal());
                break;
            case 3:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory4Mokko());
                break;
            case 4:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory4Mokry());
                break;
            default: break;
        }
        return sum;


    }

    public Long cat5ColorBy(NewOrderParam newOrderParam, NewCom newCom) {

        Byte colorNumber = newOrderParam.getColorNumber();

        Long sum = null;

        switch (colorNumber) {

            case 1:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory5Oq());
                break;
            case 2:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory5Zal());
                break;
            case 3:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory5Mokko());
                break;
            case 4:
                sum = (newOrderParam.getHeight() * newOrderParam.getWidth() * newCom.getCategory5Mokry());
                break;
            default: break;
        }
        return sum;


    }


    public Long tokchaBy(NewOrderParam newOrderParam, NewCom newCom) {
        Long sum = null;
        switch (newOrderParam.getColorNumber()) {

            case 1:
                switch (newOrderParam.getShelfSize()) {

                    case 15: sum = newOrderParam.getWidth() * newCom.getShelf15Oq();
                        break;

                    case 20: sum = newOrderParam.getWidth() * newCom.getShelf20Oq();
                        break;

                    case 25: sum = newOrderParam.getWidth() * newCom.getShelf25Oq();
                        break;

                    case 30: sum = newOrderParam.getWidth() * newCom.getShelf30Oq();
                        break;

                    case 35: sum = newOrderParam.getWidth() * newCom.getShelf35Oq();
                        break;

                    case 40: sum = newOrderParam.getWidth() * newCom.getShelf40Oq();
                        break;

                    case 45: sum = newOrderParam.getWidth() * newCom.getShelf45Oq();
                        break;

                    default:break;
                }
                break;


            case 2:
                switch (newOrderParam.getShelfSize()) {

                    case 15: sum = newOrderParam.getWidth() * newCom.getShelf15Zal();
                        break;

                    case 20: sum = newOrderParam.getWidth() * newCom.getShelf20Zal();
                        break;

                    case 25: sum = newOrderParam.getWidth() * newCom.getShelf25Zal();
                        break;

                    case 30: sum = newOrderParam.getWidth() * newCom.getShelf30Zal();
                        break;

                    case 35: sum = newOrderParam.getWidth() * newCom.getShelf35Zal();
                        break;

                    case 40: sum = newOrderParam.getWidth() * newCom.getShelf40Zal();
                        break;

                    case 45: sum = newOrderParam.getWidth() * newCom.getShelf45Zal();
                        break;

                    default:break;
                }
                break;


            case 3:
                switch (newOrderParam.getShelfSize()) {

                    case 15: sum = newOrderParam.getWidth() * newCom.getShelf15Mokko();
                        break;

                    case 20: sum = newOrderParam.getWidth() * newCom.getShelf20Mokko();
                        break;

                    case 25: sum = newOrderParam.getWidth() * newCom.getShelf25Mokko();
                        break;

                    case 30: sum = newOrderParam.getWidth() * newCom.getShelf30Mokko();
                        break;

                    case 35: sum = newOrderParam.getWidth() * newCom.getShelf35Mokko();
                        break;

                    case 40: sum = newOrderParam.getWidth() * newCom.getShelf40Mokko();
                        break;

                    case 45: sum = newOrderParam.getWidth() * newCom.getShelf45Mokko();
                        break;

                    default:break;
                }
                break;


            case 4:
                switch (newOrderParam.getShelfSize()) {

                    case 15: sum = newOrderParam.getWidth() * newCom.getShelf15Mokry();
                        break;

                    case 20: sum = newOrderParam.getWidth() * newCom.getShelf20Mokry();
                        break;

                    case 25: sum = newOrderParam.getWidth() * newCom.getShelf25Mokry();
                        break;

                    case 30: sum = newOrderParam.getWidth() * newCom.getShelf30Mokry();
                        break;

                    case 35: sum = newOrderParam.getWidth() * newCom.getShelf35Mokry();
                        break;

                    case 40: sum = newOrderParam.getWidth() * newCom.getShelf40Mokry();
                        break;

                    case 45: sum = newOrderParam.getWidth() * newCom.getShelf45Mokry();
                        break;

                    default:break;
                }
                break;
            default:break;

        }
        return sum;

    }


    public Long hisoblash(NewOrderParam newOrderParam, NewCom newCom) {

        Integer category = newOrderParam.getCategory();

        Long totalSum = null;

        switch (category) {

            case 1:
            case 2:
                totalSum = cat1ColorBy(newOrderParam, newCom);
                break;

            case 3:
            case 4:
                totalSum = cat1ColorBy(newOrderParam, newCom);
                totalSum = totalSum + newCom.getMexanizmPrice();
                break;

            case 5:
                totalSum = cat2ColorBy(newOrderParam, newCom);
                break;
            case 6:
                totalSum = cat2ColorBy(newOrderParam, newCom);
                totalSum = totalSum + newCom.getMexanizmPrice();
                break;
            case 7:
                totalSum = cat3ColorBy(newOrderParam, newCom);
                break;
            case 8:
                totalSum = cat3ColorBy(newOrderParam, newCom);
                totalSum = (totalSum + newCom.getMexanizmPrice() * 2);
                break;

            case 9:
            case 10:
                totalSum = cat3ColorBy(newOrderParam, newCom);
                totalSum = totalSum + newCom.getMexanizmPrice();
                break;

            case 11:
                totalSum = cat4ColorBy(newOrderParam, newCom);
                break;

            case 12:
                totalSum = cat4ColorBy(newOrderParam, newCom);
                totalSum = totalSum + newCom.getMexanizmPrice();
                break;

            case 13:
                totalSum = cat5ColorBy(newOrderParam, newCom);
                break;

            case 14:
                totalSum = cat5ColorBy(newOrderParam, newCom);
                totalSum = (totalSum + newCom.getMexanizmPrice() * 2);
                break;
            case 15:
            case 16:
                totalSum = cat5ColorBy(newOrderParam, newCom);
                totalSum = totalSum + newCom.getMexanizmPrice();
                break;

            default:break;



        }

        totalSum +=  tokchaBy(newOrderParam,newCom);

        return totalSum;



    }

}

























