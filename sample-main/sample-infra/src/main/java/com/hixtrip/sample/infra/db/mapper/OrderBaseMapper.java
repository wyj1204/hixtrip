package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.OrderBaseDo;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper示例
 */
@Mapper
public interface OrderBaseMapper extends BaseMapper<OrderBaseDo> {
}
