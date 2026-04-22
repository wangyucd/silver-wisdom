export interface AdminLoginRequest {
  username: string;
  password: string;
}

export interface AdminLoginResponse {
  token: string;
  adminId: number;
  name: string;
  loginType: string;
}

export interface AdminProfileResponse {
  adminId: number;
  username: string;
  name: string;
  status: string;
  loginType: string;
}

export interface UserInterestTag {
  id?: number;
  userId?: number;
  tag: string;
  weight: number;
  source?: string;
}

export interface UserLearningSummary {
  generatedCount: number;
  contentViewCount: number;
  lastLearnTime?: string;
}

export interface UserListItem {
  userId: number;
  nickname: string;
  status: string;
  tagSummary: string[];
  lastLoginTime?: string;
  createdAt?: string;
}

export interface UserPageResponse {
  total: number;
  list: UserListItem[];
}

export interface UserDetailResponse {
  userId: number;
  nickname: string;
  avatarUrl?: string;
  status: string;
  tagList: UserInterestTag[];
  learningSummary: UserLearningSummary;
  createdAt?: string;
  lastLoginTime?: string;
}

export interface UpdateUserStatusRequest {
  status: string;
  reason: string;
}

export interface HotspotEntity {
  id?: number;
  title: string;
  summary: string;
  coverUrl: string;
  weight: number;
  startTime?: string;
  endTime?: string;
  status?: number;
  createdBy?: number;
}

export interface CategoryEntity {
  id?: number;
  name: string;
  iconUrl: string;
  coverUrl: string;
  sort: number;
  status?: number;
}

export interface ContentItemEntity {
  id?: number;
  title: string;
  summary: string;
  coverUrl: string;
  type: string;
  contentBody: string;
  videoUrl: string;
  categoryId?: number;
  heatScore: number;
  publishTime?: string;
  publishStatus?: string;
  createdBy?: number;
  tags: string[];
}

export interface HotspotUpsertRequest {
  title: string;
  summary: string;
  coverUrl: string;
  weight: number;
}

export interface CategoryUpsertRequest {
  name: string;
  iconUrl: string;
  coverUrl: string;
  sort: number;
}

export interface ContentUpsertRequest {
  title: string;
  summary: string;
  coverUrl: string;
  type: string;
  contentBody: string;
  videoUrl: string;
  categoryId: number;
  heatScore: number;
  tags: string[];
}

export interface ContentListQuery {
  categoryId?: number;
  sortBy?: string;
  pageNum?: number;
  pageSize?: number;
}

export interface ContentPageResponse {
  total: number;
  list: ContentItemEntity[];
}

export interface AiGenerateResultEntity {
  title?: string;
  summary?: string;
  contentBody?: string;
  coverUrl?: string;
}

export interface AiGenerateTaskResponse {
  taskId: string;
  status: string;
  result?: AiGenerateResultEntity;
  failReason?: string;
}

export interface GeneratedLearnItem {
  taskId: string;
  title: string;
  summary: string;
  createdAt?: string;
}

export interface GeneratedLearnPageResponse {
  total: number;
  list: GeneratedLearnItem[];
}
