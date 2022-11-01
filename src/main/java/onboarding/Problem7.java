package onboarding;

import java.util.*;

public class Problem7 {
    public static final int ACQUAINTANCE_SCORE = 10;
    public static final int VISITOR_SCORE = 1;

    public static List<String> solution(String user, List<List<String>> friends, List<String> visitors) {
        List<String> answer = Collections.emptyList();
        HashMap<String, List<String>> friendRelationshipMap = new HashMap<>();
        HashMap<String, Integer> scoreMap = new HashMap<>();

        getFriendRelationshipMap(user, friends, friendRelationshipMap, scoreMap);
        increaseAcquaintanceScore(user, friendRelationshipMap, scoreMap);
        increaseVisitorScore(user, friendRelationshipMap, scoreMap, visitors);

        getRecommendedFriends(scoreMap, answer);
        sortRecommendedFriends(scoreMap, answer);
        return answer;
    }

    private static void getFriendRelationshipMap(String user, List<List<String>> friends, HashMap<String, List<String>> friendRelationshipMap, HashMap<String, Integer> scoreMap) {
        for (List<String> friend : friends) {
            String userA = friend.get(0);
            String userB = friend.get(1);

            initializeScoreMap(user, scoreMap, userA, userB);

            if (friendRelationshipMap.containsKey(userA)) {
                friendRelationshipMap.get(userA).add(userB);
            } else {
                friendRelationshipMap.put(userA, new ArrayList<>(List.of(userB)));
            }

            if (friendRelationshipMap.containsKey(userB)) {
                friendRelationshipMap.get(userB).add(userA);
            } else {
                friendRelationshipMap.put(userB, new ArrayList<>(List.of(userA)));
            }
        }
    }

    private static void initializeScoreMap(String user, HashMap<String, Integer> scoreMap, String userA, String userB) {
        if (userA.equals(user) == false) {
            scoreMap.put(userA, 0);
        }

        if (userB.equals(user) == false) {
            scoreMap.put(userB, 0);
        }
    }

    private static void increaseAcquaintanceScore(String user, HashMap<String, List<String>> friendRelationshipMap, HashMap<String, Integer> scoreMap) {
        List<String> userFriends = friendRelationshipMap.get(user);

        for (String userFriend : userFriends) {
            List<String> acquaintance = friendRelationshipMap.get(userFriend);

            for (String ac : acquaintance) {
                if (ac.equals(user)) {
                    continue;
                } else if (userFriends.contains(ac)) {
                    continue;
                }

                int currentScore = scoreMap.get(ac);
                scoreMap.put(ac, currentScore + ACQUAINTANCE_SCORE);
            }
        }
    }

    private static void increaseVisitorScore(String user, HashMap<String, List<String>> friendRelationshipMap, HashMap<String, Integer> scoreMap, List<String> visitors) {
        List<String> userFriends = friendRelationshipMap.get(user);

        for (String visitor : visitors) {
            if (userFriends.contains(visitor)) {
                continue;
            }

            if (scoreMap.containsKey(visitor)) {
                int currentScore = scoreMap.get(visitor);
                scoreMap.put(visitor, currentScore + VISITOR_SCORE);
            } else {
                scoreMap.put(visitor, VISITOR_SCORE);
            }
        }
    }

    private static void getRecommendedFriends(HashMap<String, Integer> scoreMap, List<String> answer) {
        Iterator iterator = scoreMap.keySet().iterator();

        while (iterator.hasNext()) {
            String id = (String)iterator.next();

            if (scoreMap.containsKey(id) && scoreMap.get(id) != 0) {
                answer.add(id);
            }
        }
    }

    private static void sortRecommendedFriends(HashMap<String, Integer> scoreMap, List<String> answer) {
        for (int leftIdx = 0; leftIdx <= answer.size() - 2; leftIdx++) {
            for (int rightIdx = leftIdx + 1; rightIdx <= answer.size() - 1; rightIdx++) {
                String left = answer.get(leftIdx);
                String right = answer.get(rightIdx);

                if (scoreMap.get(left) < scoreMap.get(right)) {
                    String tmp = left;
                    answer.set(leftIdx, right);
                    answer.set(rightIdx, tmp);
                } else if (scoreMap.get(left) == scoreMap.get(right)) {
                    if (right.compareTo(left) < 0) {
                        String tmp = left;
                        answer.set(leftIdx, right);
                        answer.set(rightIdx, tmp);
                    }
                }
            }
        }

    }
}
