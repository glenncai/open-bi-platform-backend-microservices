package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Chart table
 * @TableName t_chart
 */
@TableName(value ="t_chart")
@Data
public class Chart implements Serializable {
    /**
     * Primary key
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Analysis target
     */
    private String goal;

    /**
     * Chart name
     */
    private String name;

    /**
     * Chart data
     */
    private String chartData;

    /**
     * Chart type
     */
    private String chartType;

    /**
     * Generated chart data
     */
    private String genChartData;

    /**
     * Generated chart conclusion
     */
    private String genChartConclusion;

    /**
     * 0: Waiting, 1: Running, 2: Succeed, 3: Failed
     */
    private Integer status;

    /**
     * Execution message
     */
    private String execMessage;

    /**
     * User ID
     */
    private Long userId;

    /**
     * 0: invalid, 1: valid
     */
    private Integer valid;

    /**
     * Created time
     */
    private Date createdAt;

    /**
     * Updated time
     */
    private Date updatedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}