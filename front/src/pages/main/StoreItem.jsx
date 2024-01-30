import styled from "styled-components";
import { useNavigate } from "react-router-dom";

const StoreItem = (props) => {
    const navigate = useNavigate();

    const goToStoreDetail = () => {
        navigate('/');
    }

    return (
        <StoreContainer >
            <StoreImg />
            <TextContainer>
                <StoreNameTxt>{props.name}</StoreNameTxt>
                <StoreRatingTxt>{props.rating}</StoreRatingTxt>
            </TextContainer>
        </StoreContainer>
    );
}


const StoreContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 260px;
    margin-right: 40px;
    margin-bottom: 13px;
`;

const StoreImg = styled.img`
    width: 260px;
    height: 200px;
`;

const TextContainer = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 13px;
`;

const StoreNameTxt = styled.div`
    color: #61677A;
    font-size: 20px;
    margin-left: 10px;
    font-weight: 800;
`;

const StoreRatingTxt = styled.div`
    color: #61677A;
    margin-right: 10px;
    font-weight: 800;
`;

export default StoreItem;
