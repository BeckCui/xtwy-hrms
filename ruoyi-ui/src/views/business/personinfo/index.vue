<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="人员类型" prop="filter_type_eq_s">
        <el-select clearable v-model="queryParams.filter_type_eq_s">
          <el-option
            v-for="dict in dict.type.sys_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="在职状态" prop="filter_status_eq_s">
        <el-select clearable v-model="queryParams.filter_status_eq_s">
          <el-option
            v-for="dict in dict.type.sys_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="科室" prop="filter_office_eq_s">
        <el-input v-model="queryParams.filter_office_eq_s"/>
      </el-form-item>

      <el-form-item prop="filter_name_like_s">
        <el-input v-model="queryParams.filter_name_like_s" placeholder="请输入姓名检索"/>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="text-center">
      <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
    </el-row>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:role:add']"
        >新增
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="roleList" @selection-change="handleSelectionChange">
      <el-table-column label="在职状态" prop="status" align="center" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status==='在职'?'success':'info'">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="人员类别" prop="type" align="center" width="120"/>
      <el-table-column label="姓名" prop="name" align="center" width="80"/>
      <el-table-column label="性别" prop="sex" align="center" width="80"/>
      <el-table-column label="科室" prop="office" align="center" width="80"></el-table-column>
      <el-table-column label="职务层级" prop="workLevel" align="center" width="100"></el-table-column>
      <el-table-column label="专业技术职务" prop="specialityTech" align="center" width="100"></el-table-column>
      <el-table-column label="学历" prop="education" align="center" width="100"></el-table-column>
      <el-table-column label="出生时间" prop="birthdayStr" align="center" width="130"></el-table-column>
      <el-table-column label="工作时间" prop="workDateStr" align="center" width="130"></el-table-column>
      <el-table-column label="操作" align="center" width="200" class-name="small-padding">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >科室分配
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改角色配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1100px" append-to-body>
      <el-row :gutter="24" class="title-class">基本信息</el-row>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="ID" prop="id" hidden>
          <el-input v-model="form.id"/>
        </el-form-item>
        <el-row :gutter="24">
          <el-col :span="19">
            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="姓名" prop="name">
                  <el-input v-model="form.name"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="性别" prop="sex">
                  <el-select v-model="form.sex">
                    <el-option
                      v-for="dict in dict.type.sys_user_sex"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出生日期" prop="birthday">
                  <el-date-picker style="width: 152px;"
                                  v-model="form.birthday"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="民族" prop="nation">
                  <el-select v-model="form.nation">
                    <el-option
                      v-for="dict in dict.type.sys_nation"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="政治面貌" prop="politic">
                  <el-select v-model="form.politic">
                    <el-option
                      v-for="dict in dict.type.sys_politic"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="血型" prop="bloodType">
                  <el-select v-model="form.bloodType">
                    <el-option
                      v-for="dict in dict.type.sys_blood_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="岗位职务" prop="position">
                  <el-select v-model="form.position">
                    <el-option
                      v-for="dict in dict.type.sys_position"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="工作/入伍时间" prop="workDate">
                  <el-date-picker style="width: 152px;"
                                  @change="onChangeWorkDate"
                                  v-model="form.workDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="工作/入伍年限" prop="workYear" class="text-center">
                  {{ form.workYear }}&nbsp;年
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="专业系列" prop="speciality">
                  <el-select v-model="form.speciality">
                    <el-option
                      v-for="dict in dict.type.sys_speciality"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="学历学位" prop="education">
                  <el-select v-model="form.education">
                    <el-option
                      v-for="dict in dict.type.sys_education"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="在职状态" prop="status">
                  <el-select v-model="form.status">
                    <el-option
                      v-for="dict in dict.type.sys_status"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="8">
                <el-form-item label="人员类别" prop="type">
                  <el-select v-model="form.type" @change="onChangePersonType">
                    <el-option
                      v-for="dict in dict.type.sys_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="所属科室" prop="office">
                  <el-select v-model="form.office">
                    <el-option
                      v-for="dict in dict.type.sys_office"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-show="showSalaryLevel">
                <el-form-item label="待遇级别" prop="salaryLevel">
                  <el-select v-model="form.salaryLevel">
                    <el-option
                      v-for="dict in this.dynicOption.salaryLevel"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24" v-show="showPositionInput">
              <el-col :span="8">
                <el-form-item label="岗位级别" prop="positLevel">
                  <el-select v-model="form.positLevel">
                    <el-option
                      v-for="dict in dict.type.sys_posit_level"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="岗位等级" prop="positGrade">
                  <el-select v-model="form.positGrade" placeholder="岗位等级">
                    <el-option
                      v-for="dict in dict.type.sys_posit_grade"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                  <el-date-picker style="position: absolute; left: 154px;width: 140px;"
                                  v-model="form.positGradeDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="8" v-show="showWorkLevel">
                <el-form-item label="职务层级" prop="workLevel">
                  <el-select v-model="form.workLevel">
                    <el-option
                      v-for="dict in dict.type.sys_work_level"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                  <el-date-picker style="position: absolute; left: 154px;width: 140px;"
                                  v-model="form.workLevelDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="4" v-show="showWorkLevel">
                &nbsp;是的
              </el-col>
              <el-col :span="8">
                <el-form-item label="专业技术职务" prop="specialityTech" v-show="showSpecialityTech">
                  <el-select v-model="form.specialityTech">
                    <el-option
                      v-for="dict in dict.type.sys_speciality_tech"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                  <el-date-picker style="position: absolute; left: 154px;width: 140px;"
                                  v-model="form.specialityTechDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
          <el-col :span="5">
            <el-upload
              :action=uploadUrl
              ref="headPictureRef"
              list-type="picture-card"
              :limit="1"
              :file-list="headPicture"
              :on-remove="handleRemove">
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-col>
        </el-row>
      </el-form>

      <br/>
      <br/>

      <el-row :gutter="24" class="title-class">教育培训经历
        <span class="add-edu-icon" @click="openEduFun">
        <i class="el-icon-edit-outline" style="margin-left: 10px; font-size: 17px; top: 2px; position: relative;"></i>
        &nbsp;&nbsp;添加
        </span>
      </el-row>
      <el-row :gutter="24" v-for="(item,index) in this.form.educations" class="edu-info-class">
        <el-row :gutter="24" class="font-style-1">
          <el-col :span="6">{{ item.schoolName }}</el-col>
          <el-col :span="6">{{ item.education }}</el-col>
          <el-col :span="6"><span>{{ parseTime(item.eduStartDate, '{y}') }}&nbsp;~&nbsp;{{
              parseTime(item.eduEndDate, '{y}')
            }}</span>
          </el-col>
          <el-col :span="6"></el-col>
        </el-row>
        <el-row :gutter="24" class="font-style-2">
          <el-col :span="6"><span>专业</span>{{ item.profession }}</el-col>
          <el-col :span="6">
            <span>学制</span>{{ workYearFun(item.eduYear) }}
            <span style="margin-left: 30px;">获得学位</span>
            {{ degreeFun(item.degree) }}
          </el-col>
          <el-col :span="6"><span>性质</span>{{ eduTypeFun(item.eduType) }}</el-col>
          <el-col :span="2" class="file-total-count">
            <div @click="viewFile(item.attachFiles)">
              <span>附件</span>
              <span>{{ item.attachFiles ? item.attachFiles.length : 0 }}</span>
            </div>
          </el-col>
          <div class="edu-info-action">
            <i class="el-icon-edit" @click="editEdu(item,index)"></i>
            <i class="el-icon-delete" @click="deleteEdu(index)"></i>
          </div>
        </el-row>
      </el-row>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>

    </el-dialog>

    <el-dialog :title="titleEdu" :visible.sync="openEdu" width="600px" append-to-body>
      <el-form ref="formEdu" :model="formEdu" :rules="rulesEdu" label-width="110px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="毕业院校" prop="schoolName">
              <el-input v-model="formEdu.schoolName" style="width:134px"/>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="学历" prop="eduDegree">
              <el-select clearable v-model="formEdu.eduDegree" style="width:134px">
                <el-option
                  v-for="dict in dict.type.sys_education"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="学制" prop="eduYear">
              <el-select clearable v-model="formEdu.eduYear" style="width:134px">
                <el-option
                  v-for="dict in dict.type.sys_edu_year"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="性质" prop="eduType">
              <el-select clearable v-model="formEdu.eduType" style="width:134px">
                <el-option
                  v-for="dict in dict.type.sys_edu_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="专业" prop="profession">
              <el-input v-model="formEdu.profession" style="width:134px"/>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="学位" prop="degree">
              <el-select clearable v-model="formEdu.degree" style="width:134px">
                <el-option
                  v-for="dict in dict.type.sys_degree"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="教育开始时间" prop="eduStartDate">
              <el-date-picker style="width:134px"
                              v-model="formEdu.eduStartDate"
                              type="year"
                              placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="教育结束时间" prop="eduEndDate">
              <el-date-picker style="width:140px"
                              v-model="formEdu.eduEndDate"
                              type="year"
                              placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-upload
            ref="uploadEdu"
            class="upload-demo"
            :action=uploadUrl
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :limit="5"
            :on-exceed="handleExceed"
            list-type="picture"
            :file-list="fileList">
            <el-button size="small" type="primary">上传附件</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormEdu">确 定</el-button>
        <el-button @click="cancelEdu">取 消</el-button>
      </div>
    </el-dialog>


    <el-dialog :visible.sync="openImgView" :top="'10vh'" :autoplay="false"
               :height="500" :custom-class="'view-img-panel'"
               :show-close="false" :modal="false" width="850px" append-to-body>
      <template>
        <el-carousel trigger="click" height="420px">
          <el-carousel-item v-for="{id} of currentFiles" :key="id">
            <img :src=" serverAdr + `/attachment/view/picture/${id}`" class="img-item"/>
          </el-carousel-item>
        </el-carousel>
      </template>
    </el-dialog>


  </div>
</template>

<script>
import {getDicts as getDicts} from '@/api/system/dict/data'
import moment from 'moment';
import request from "@/utils/request";
import {delUser, getUser} from "@/api/system/user";

export default {
  name: "PersonInfo",
  dicts: ['sys_posit_level', 'sys_user_sex', 'sys_salary_level', 'sys_type',
    'sys_office', 'sys_status', 'sys_education', 'sys_speciality', 'sys_position', 'sys_blood_type',
    'sys_edu_type', 'sys_edu_year', 'sys_degree',
    'sys_politic', 'sys_nation', 'sys_posit_grade', 'sys_work_level', 'sys_speciality_tech'],
  data() {
    return {
      serverAdr: process.env.VUE_APP_BASE_API,
      uploadUrl: process.env.VUE_APP_BASE_API + '/attachment/upload',
      uploadUrl1: process.env.VUE_APP_BASE_API + "/common/upload",
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 角色表格数据
      roleList: [],
      // 弹出层标题
      title: "",
      titleEdu: "",
      // 是否显示弹出层
      open: false,
      openEdu: false,
      openImgView: false,
      // 是否显示弹出层（数据权限）
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      currentFiles: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleName: undefined,
        roleKey: undefined,
        type: undefined,
        filter_type_eq_s: undefined,
        filter_office_eq_s: undefined,
        filter_name_like_s: undefined,
        filter_status_eq_s: undefined
      },
      // 表单参数
      form: {status: 1},
      formEdu: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        name: [{required: true, message: "不能为空", trigger: "blur"}],
        sex: [{required: true, message: "不能为空", trigger: "blur"}],
        birthday: [{required: true, message: "不能为空", trigger: "blur"}],
        nation: [{required: true, message: "不能为空", trigger: "blur"}],
        politic: [{required: true, message: "不能为空", trigger: "blur"}],
        bloodType: [{required: true, message: "不能为空", trigger: "blur"}],
        position: [{required: true, message: "不能为空", trigger: "blur"}],
        workDate: [{required: true, message: "不能为空", trigger: "blur"}],
        workYear: [{required: true, message: "不能为空", trigger: "blur"}],
        speciality: [{required: true, message: "不能为空", trigger: "blur"}],
        education: [{required: true, message: "不能为空", trigger: "blur"}],
        status: [{required: true, message: "不能为空", trigger: "blur"}],
        type: [{required: true, message: "不能为空", trigger: "blur"}],
        office: [{required: true, message: "不能为空", trigger: "blur"}],
      },

      rulesEdu: {
        schoolName: [
          {required: true, message: "毕业院校不能为空", trigger: "blur"}
        ],
        eduDegree: [
          {required: true, message: "学历不能为空", trigger: "blur"}
        ],
        eduYear: [
          {required: true, message: "学制不能为空", trigger: "blur"}
        ],
        degree: [
          {required: true, message: "学位不能为空", trigger: "blur"}
        ],
        profession: [
          {required: true, message: "专业不能为空", trigger: "blur"}
        ],
        eduStartDate: [
          {required: true, message: "教育开始时间不能为空", trigger: "blur"}
        ],
        eduEndDate: [
          {required: true, message: "教育结束时间不能为空", trigger: "blur"}
        ],
        eduType: [
          {required: true, message: "性质不能为空", trigger: "blur"}
        ]

      },
      headPicture: [],
      fileList: [],
      eduList: [],
      imageUrl: undefined,
      dynicOption: {
        salaryLevel: [],
      },
      showPositionInput: false,
      showSalaryLevel: false,
      showWorkLevel: false,
      showSpecialityTech: false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      const {pageNum, pageSize} = this.queryParams;
      const {
        type: {
          sys_user_sex,
          sys_type,
          sys_office,
          sys_status,
          sys_education,
          sys_work_level,
          sys_speciality_tech
        }
      } = this.dict;

      this.loading = true;
      request({
        url: `/personinfo/page/${pageNum}`,
        method: 'get',
        params: {...this.queryParams, length: pageSize}
      }).then(({data: {datas: dataList, total}}) => {

        dataList.forEach(item => {
          const status = sys_status.filter(p => p.value === item.status)
          const type = sys_type.filter(p => p.value === item.type)
          const sex = sys_user_sex.filter(p => p.value === item.sex)
          const office = sys_office.filter(p => p.value === item.office)
          const workLevel = sys_work_level.filter(p => p.value === item.workLevel)
          const tech = sys_speciality_tech.filter(p => p.value === item.specialityTech)
          const edu = sys_education.filter(p => p.value === item.education)


          item.status = status.length > 0 ? status[0].label : '';
          item.type = type.length > 0 ? type[0].label : '';
          item.sex = sex.length > 0 ? sex[0].label : '';
          item.office = office.length > 0 ? office[0].label : '';
          item.workLevel = workLevel.length > 0 ? workLevel[0].label : '';
          item.specialityTech = tech.length > 0 ? tech[0].label : '';
          item.education = edu.length > 0 ? edu[0].label : '';


        });

        this.roleList = dataList;
        this.total = total;
        this.loading = false;
      });

    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮（数据权限）
    cancelDataScope() {
      this.openDataScope = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        roleId: undefined,
        roleName: undefined,
        roleKey: undefined,
        roleSort: 0,
        status: "1",
        menuIds: [],
        deptIds: [],
        menuCheckStrictly: true,
        deptCheckStrictly: true,
        remark: undefined,
        workYear: 0
      };
      this.headPicture = []
      this.resetForm("form");
    },
    resetEdu() {
      this.formEdu = {
        schoolName: undefined,
        education: undefined,
        eduYear: undefined,
        eduType: undefined,
        profession: undefined,
        degree: undefined,
        eduStartDate: undefined,
        eduEndDate: undefined,
        filesCount: undefined,
        rowIndex: undefined
      };
      this.fileList = [];
      this.resetForm("formEdu");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 修改按钮操作 */
    handleUpdate({id}) {
      this.reset();
      request({
        url: `/personinfo/get/${id}`,
        method: 'get',
        params: {joinDomain: 'educations,headPicture'}
      }).then(({data}) => {
        if (data.headPicture) {
          this.headPicture = [{
            fileId: data.headPicture.id,
            name: data.headPicture.fileName,
            url: `${this.serverAdr}/attachment/view/picture/${data.headPicture.id}`
          }];
        }
        this.title = '修改人员档案'
        this.form = data;
        this.open = true;
      });
    },

    handleDelete(row) {
      const userIds = row.id || this.ids;
      this.$modal.confirm('是否确认删除用户编号为"' + userIds + '"的数据项？').then(function () {
        return delUser(userIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },

    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.roleId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleDataScope":
          this.handleDataScope(row);
          break;
        case "handleAuthUser":
          this.handleAuthUser(row);
          break;
        default:
          break;
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增人员档案";
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          const files = this.$refs.headPictureRef.uploadFiles;
          if (files && files.length > 0) {
            this.form.headPicture = files.map(p => ({id: p.response ? p.response.data.id : p.fileId}))[0];
          }
          request({
            url: '/personinfo/add',
            method: 'post',
            data: this.form
          }).then(() => {
            this.open = false;
            this.$modal.msgSuccess(this.form.id ? "修改成功" : "添加成功");
            this.getList();
          });

        }
      });
    },

    submitFormEdu() {

      const files = this.$refs.uploadEdu.uploadFiles;
      const fileIds = files.map(p => ({id: p.response ? p.response.data.id : p.fileId}));
      this.$refs["formEdu"].validate(valid => {
        if (valid) {
          const newFormEdu = {...this.formEdu, attachFiles: fileIds, filesCount: files.length};
          if (this.formEdu.rowIndex != undefined) {
            this.form.educations.splice(this.formEdu.rowIndex, 1, newFormEdu);
          } else {
            this.form.educations.push(newFormEdu);
          }

          this.openEdu = false;
          this.resetEdu();
        }
      });
    },

    openEduFun() {
      this.openEdu = true;
      this.currentFiles = []
      this.titleEdu = '新增教育经历'
    },

    cancelEdu() {
      this.openEdu = false;
      this.resetEdu();
    },

    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    viewFile(files) {

      if (files && files.length > 0) {
        this.currentFiles = files;
        this.openImgView = true;
      }

    },
    editEdu: function (item, index) {
      this.openEdu = true;
      this.resetEdu();
      this.titleEdu = '修改教育经历'
      const {attachFiles} = item;
      if (attachFiles) {
        this.fileList = attachFiles.map(p => ({
          fileId: p.id,
          name: p.fileName,
          url: `${this.serverAdr}/attachment/view/picture/${p.id}`
        }))
      }
      this.formEdu = {...item, rowIndex: index};
    },

    deleteEdu: function (index) {
      const edus = this.eduList;
      return this.$confirm(`确定删除？`).then(function () {
        edus.splice(index, 1);
      });
    },

    onChangePersonType() {
      var salaryDic, selectedType = this.form.type;
      getDicts('sys_salary_level').then(rs => {
        const options = rs.data.map(p => ({
          'value': p.dictValue,
          'label': p.dictLabel
        }));
        salaryDic = options.filter(p => p.value.indexOf(selectedType + '_') > -1)
        this.dynicOption.salaryLevel = salaryDic;
      })

      this.showPositionInput = false;
      this.showSalaryLevel = false;
      this.showWorkLevel = false;
      this.showSpecialityTech = false;

      if (selectedType == '1') {
        this.showSalaryLevel = true;
        this.showWorkLevel = true;
        this.showSpecialityTech = true;
      } else if (selectedType == '2') {
        this.showSalaryLevel = true;
        this.showWorkLevel = true;
      } else if (selectedType == '3') {
        this.showSpecialityTech = true;
      }

    },
    onChangeWorkDate() {
      if (this.form.workDate) {
        const years = moment(this.form.workDate).diff(moment(), 'years');
        this.form.workYear = -years;
      } else {
        this.form.workYear = 0;
      }
    },
    workYearFun(yearIndex) {
      return this.dict.type.sys_edu_year.filter(p => p.value === yearIndex)[0].label;
    },
    eduTypeFun(typeIndex) {
      return this.dict.type.sys_edu_type.filter(p => p.value === typeIndex)[0].label;
    },
    degreeFun(degreeIndex) {
      return this.dict.type.sys_degree.filter(p => p.value === degreeIndex)[0].label;
    }

  }
};
</script>

<style scoped lang="scss">
.title-class {
  margin-left: 0px !important;
  margin-bottom: 30px;
  height: 25px;
  border-left: 4px solid #3aa0fd;
  line-height: 28px;
  padding-left: 4px;
  font-size: 13px;
}

.add-edu-icon {
  color: #00afff;

  &:hover {
    cursor: pointer;
  }
}


.edu-info-class {

  margin: 10px 45px !important;
  border-bottom: solid 1px #DCDFE6;
  padding-bottom: 10px;

  .font-style-1 {
    color: #606266;
    font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, Arial, sans-serif;
    font-weight: 700;
    margin-bottom: 10px;
    font-size: 13px;

    span:last-of-type {
      color: rgb(174, 174, 174);
      position: relative;
      top: 2px;
      font-weight: 200;
    }
  }

  .font-style-2 {
    font-size: 12px;

    .file-total-count {
      &:hover {
        cursor: pointer;
      }

      span:last-of-type {
        color: #00afff;
      }
    }

    span {
      margin-right: 10px;
    }

    .edu-info-action {
      color: #00afff;
      display: inline-block;
      float: right;
      margin-right: 25px;
      font-size: 13px;

      .el-icon-edit {
        margin-right: 15px;

        &:hover {
          cursor: pointer;
        }
      }

      .el-icon-delete:hover {
        cursor: pointer;
      }
    }

  }


}


</style>
