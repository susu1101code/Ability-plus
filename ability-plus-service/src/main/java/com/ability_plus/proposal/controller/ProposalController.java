package com.ability_plus.proposal.controller;


import com.ability_plus.proposal.entity.PO.ProposalPO;
import com.ability_plus.proposal.service.IProposalService;
import com.ability_plus.utils.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/proposal")
@Api(value="proposal")
public class ProposalController {
    @Autowired
    IProposalService proposalService;

    @ApiOperation("create proposal")
    public RestResponse createProjectRequest(@RequestBody ProposalPO po){

        proposalService.createProjectRequest(po);
        return RestResponse.success();
    }
}