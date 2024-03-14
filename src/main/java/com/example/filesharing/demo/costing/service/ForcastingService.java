package com.example.filesharing.demo.costing.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.filesharing.demo.costing.entity.ForcastingEntity;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Service
public class ForcastingService {

    private double day = 1;
    private double week = 5;
	private double month;
	private double year;

    public void calculate(){
        month = Math.round(30.43 / 7 * week);
        year = month * 12;

        log.info("\nDay {}, Week {}, Month {}, Year {}\n", day, week, month, year);

        List<ForcastingEntity> forcastings = new ArrayList<>();

		forcastings.add(new ForcastingEntity(20000, "Y"));
		forcastings.add(new ForcastingEntity(2000, "M"));
		forcastings.add(new ForcastingEntity(50, "H"));
		forcastings.add(new ForcastingEntity(2000, "W"));
		forcastings.add(new ForcastingEntity(100, "D"));

        for(ForcastingEntity forcasting : forcastings){
			log.info("\n\n===> Amount {}, Unit {},\n Daily {} Hourly {} Weekly {} Monthly {} Yearly {}\n",
				forcasting.getAmount(),
				forcasting.getUnit(),
				round(findForcasting(forcasting, "D")),
                round(findForcasting(forcasting, "H")),
				round(findForcasting(forcasting, "W")),
				round(findForcasting(forcasting, "M")),
				round(findForcasting(forcasting, "Y"))
			);
		}
    }

	private double findDaily(ForcastingEntity forcasting){
        if("H".equals(forcasting.getUnit())){
            return forcasting.getAmount() * findUnit(forcasting);    
        }
        if("D".equals(forcasting.getUnit())){
            return forcasting.getAmount();
        }
		return forcasting.getAmount() / findUnit(forcasting);
	}

	private double findHourly(ForcastingEntity forcasting){
		return findDaily(forcasting) / day;
	}

	private double findWeekly(ForcastingEntity forcasting){
		return findDaily(forcasting) * week;
	}

	private double findMonthly(ForcastingEntity forcasting){
		return findDaily(forcasting) * month;
	}

	private double findYearly(ForcastingEntity forcasting){
		return findDaily(forcasting) * year;
	}

	private double findForcasting(ForcastingEntity forcasting, String unit){
		switch (unit) {
			case "H":
				return findHourly(forcasting);
			case "D":
				return findDaily(forcasting);
			case "W":
				return findWeekly(forcasting);
			case "M":
				return findMonthly(forcasting);
			case "Y":
				return findYearly(forcasting);
			default:
				break;
		}
		return 0;
	}

	private double findUnit(ForcastingEntity forcasting){
		switch (forcasting.getUnit()) {
			case "H":
				return day;
			case "D":
				return day;
			case "W":
				return week;
			case "M":
				return month;
			case "Y":
				return year;
			default:
				return 0L;
		}
	}

	private double round(double value) {
		return round(value, 2);
	}

	private double round(double value, int places) {
		if (places < 0){
			return value;
		}

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

}

// =IF(
//     B12="H";
//     A12*$B$2;
//     IF(
//         B12="W";
//         A12/$B$3;
//         IF(
//             B12="M";
//             A12/$B$4;
//             IF(B12="Y";
//                 A12/$B$5;
//                 A12
//             )
//         )
//     )
// )