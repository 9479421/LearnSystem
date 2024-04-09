package vip.wqby.learnserver.model.auto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author astupidcoder
 * @since 2022-09-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Course extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "courseId", type = IdType.AUTO)
    private Integer courseId;

    private String name;

    private String teacher;

    @TableField("coverImg")
    private String coverImg;

    private String intro;

    @TableField("isPutAway")
    private Boolean isPutAway;

}
