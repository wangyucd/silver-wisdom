interface IAppOption {
  globalData: {
    token: string;
    userInfo: UserProfile | null;
  };
}

interface ApiResponse<T> {
  code: number;
  message: string;
  data: T;
}

interface LoginResponse {
  token: string;
  userId: number;
  newUser: boolean;
  loginType: string;
}

interface CurrentUserResponse {
  userId: number;
  openId: string;
  nickname: string;
  avatarUrl?: string;
  status: string;
  newUser: boolean;
}

interface UserInterestTag {
  id?: number;
  userId?: number;
  tag: string;
  weight: number;
  source?: string;
}

interface UserTagResponse {
  tags: UserInterestTag[];
}

interface CategoryEntity {
  id: number;
  name: string;
  iconUrl?: string;
  coverUrl?: string;
  sort: number;
  status?: number;
}

interface HotspotEntity {
  id: number;
  title: string;
  summary: string;
  coverUrl?: string;
  weight: number;
  startTime?: string;
  endTime?: string;
}

interface ContentItemEntity {
  id: number;
  title: string;
  summary: string;
  coverUrl?: string;
  type: string;
  contentBody?: string;
  videoUrl?: string;
  categoryId?: number;
  heatScore: number;
  publishTime?: string;
  publishStatus?: string;
  tags: string[];
}

interface ContentPageResponse {
  total: number;
  list: ContentItemEntity[];
}

interface ReferenceResponse {
  contentId: number;
  title: string;
  snippet: string;
}

interface AiChatResponse {
  answer: string;
  references: ReferenceResponse[];
}

interface AiGenerateTaskResult {
  title?: string;
  summary?: string;
  contentBody?: string;
  coverUrl?: string;
}

interface AiGenerateTaskResponse {
  taskId: string;
  status: string;
  result?: AiGenerateTaskResult;
  failReason?: string;
}

interface GeneratedLearnItem {
  taskId: string;
  title: string;
  summary: string;
  createdAt?: string;
}

interface GeneratedLearnPageResponse {
  total: number;
  list: GeneratedLearnItem[];
}

interface UserProfile {
  userId: number;
  nickname: string;
  avatarUrl?: string;
  status: string;
  newUser: boolean;
}
