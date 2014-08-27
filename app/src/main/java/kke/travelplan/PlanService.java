package kke.travelplan;

import java.util.Map;

import kke.travelplan.util.DateFormats;
import kke.travelplan.util.JsonHttpUtil;
import kke.travelplan.util.JsonResponse;

/**
 * Plan을 서버로부터 가져오거나 넣는 관리 작업 서비스 클래스.
 */
public class PlanService {
    private static PlanService instance;

    public static PlanService getInstance() {
        if (instance == null) {
            instance = new PlanService();
        }
        return instance;
    }

    private PlanService() {
    }

    public Plan get(int id) {
        String url = App.urlPrefix + "/plan/get.tpg?id=" + id;
        JsonResponse resp = JsonHttpUtil.get(url);
        Map<String, Object> map = (Map<String, Object>) resp.get("plan");
        Plan plan = new Plan();
        plan.setId((Integer) map.get("id"));
        plan.setTitle((String) map.get("title"));
        plan.setLocation((String) map.get("location"));
        plan.setStartDate(DateFormats.parseDate((String) map.get("startDate")));
        plan.setEndDate(DateFormats.parseDate((String) map.get("endDate")));
        plan.setPublic_((Boolean) map.get("public_"));
        return plan;
    }
}
