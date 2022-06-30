package com.ability_plus.projectRequest.service;


import com.ability_plus.projectRequest.entity.PO.ProjectCreatePO;
import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
public interface IProjectRequestService extends IService<ProjectRequest> {
    /**
     * create project request
     * @param po
     */
    public void createProjectRequest(ProjectCreatePO po);

    /**
     * send project information to font-end
     * @param id    project id
     * @return      VO
     */
    public ProjectInfoVO getProjectInfo(Integer id);

}