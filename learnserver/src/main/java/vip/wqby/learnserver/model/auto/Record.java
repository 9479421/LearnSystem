package vip.wqby.learnserver.model.auto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author astupidcoder
 * @since 2022-11-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Accessors(chain = true)
public class Record extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("memberId")
    private String memberId;

    @TableField("courseId")
    private String courseId;

    @TableField("chapterId")
    private String chapterId;

    @TableField("currentTime")
    private String currentTime;

    @TableField("allTime")
    private String allTime;

    @TableField("isWatched")
    private Boolean isWatched;


}
