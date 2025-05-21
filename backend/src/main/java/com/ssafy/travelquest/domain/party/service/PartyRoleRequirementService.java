package com.ssafy.travelquest.domain.party.service;

import com.ssafy.travelquest.domain.party.dto.RequiredJobResponse;
import com.ssafy.travelquest.domain.party.repository.PartyRoleRequirementRepository;
import com.ssafy.travelquest.domain.user.entity.MBTI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PartyRoleRequirementService {
    private final PartyRoleRequirementRepository partyRoleRequirementRepository;

    public List<RequiredJobResponse> getRequiredJobs(Long partyId) {
        List<RequiredJobResponse> rawList = partyRoleRequirementRepository.findRequiredJobs(partyId);
        List<RequiredJobResponse> result = new ArrayList<>();
        Map<String, List<String>> jobToMbtiMap = MBTI.getJobToMbtiMap();

        for (RequiredJobResponse job : rawList) {
            String jobName = job.getJob();
            List<String> mbtiList = jobToMbtiMap.get(jobName);
            if (mbtiList != null) {
                result.add(job.toBuilder()
                        .mbtiList(mbtiList)
                        .build());
            }
        }
        return result;
    }

}
