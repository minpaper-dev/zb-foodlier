import { useEffect, useRef, useState } from 'react'
import Header from '../../components/Header'
import BottomNavigation from '../../components/BottomNavigation'
import * as S from '../../styles/chat/ChattingPage.styled'
import ChatRoom from '../../components/chat/ChatRoom'
import RoomListItem, {
  RoomInfoInterface,
} from '../../components/chat/RoomListItem'
import axiosInstance from '../../utils/FetchCall'
import DmTest2 from './DmTest2'

const ChattingPage = () => {
  const [currentChat, setCurrentChat] = useState(1)
  const [dmRoomList, setDmRoomList] = useState<RoomInfoInterface[]>([])
  const [isLoading, setIsLoading] = useState(false)
  const firstDmRoomRef = useRef<HTMLButtonElement>(null)
  // const [isFirstDmRoomRef, setIsFirstDmRoomRef] = useState<HTMLDivElement>(null)

  const getDmRoomList = async () => {
    try {
      const res = await axiosInstance.get('/dm/room/0/10')
      if (res.status === 200) {
        console.log('여기 맞지?', res.data)
        setDmRoomList(res.data)
        setIsLoading(true)
      }
    } catch (error) {
      console.log(error)
    }
  }

  useEffect(() => {
    getDmRoomList()
  }, [])

  useEffect(() => {
    if (dmRoomList.length > 0 && firstDmRoomRef.current) {
      firstDmRoomRef.current.click()
    }
  }, [dmRoomList])

  return (
    <>
      <Header />
      <S.Container>
        <S.WrapChatList>
          {isLoading &&
            dmRoomList.map((item, index) => (
              <S.Wrap key={item.id}>
                <S.ReqireButton>요청서 보기</S.ReqireButton>
                <S.RoomListButton
                  ref={index === 0 ? firstDmRoomRef : null}
                  onClick={() => setCurrentChat(item.id)}
                  $isActive={currentChat === item.id}
                >
                  <RoomListItem roomInfo={item} />
                </S.RoomListButton>
              </S.Wrap>
            ))}
        </S.WrapChatList>
        {isLoading && (
          <ChatRoom
            roomInfo={dmRoomList.find(item => item.id === currentChat)}
          />
        )}
      </S.Container>
      <DmTest2 />
      <BottomNavigation />
    </>
  )
}

export default ChattingPage
