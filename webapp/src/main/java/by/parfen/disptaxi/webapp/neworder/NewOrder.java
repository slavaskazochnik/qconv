package by.parfen.disptaxi.webapp.neworder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.injection.Injector;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.Route;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.services.PriceService;
import by.parfen.disptaxi.services.UserProfileService;

public class NewOrder extends NewOrderClass implements NewOrderService {

	@Inject
	private AutoService autoService;
	@Inject
	private PriceService priceService;
	@Inject
	private UserProfileService userProfileService;

	public NewOrder() {
		Injector.get().inject(this);
		route = new Route();
		routePoints = new ArrayList<String>();
	}

	@Override
	public Price getPrice(CarType carType) {
		Price result = null;
		List<Price> prices;
		prices = priceService.getAllByCarType(carType);
		if (prices != null && prices.size() > 0) {
			result = prices.get(0);
		}
		return result;
	}

	@Override
	public Price getPrice(Auto auto) {
		return getPrice(auto.getCar().getCarType());
	}

	@Override
	public List<Auto> getAllAuto(CarType carType) {
		return autoService.getAllActiveByCarType(carType);
	}

	@Override
	public List<Auto> getAllAuto(Route route, CarType carType) {
		if (route != null && route.getSrcPoint() != null) {
			return autoService.getAllActiveByCarTypeAndGeo(carType, route.getSrcPoint().getPositionLat(), route.getSrcPoint()
					.getPositionLng());
		} else {
			return autoService.getAllActiveByCarType(carType);
		}
	}

	@Override
	public Auto getAutoWithDetails(Auto auto) {
		return autoService.getWithDetails(auto);
	}

	@Override
	public UserProfile getUserProfile(Long userId) {
		return userProfileService.get(userId);
	}

	@Override
	public void insertRouteIntoDB() {
		// TODO Auto-generated method stub

	}

}
